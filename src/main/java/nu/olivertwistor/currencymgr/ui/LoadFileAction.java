package nu.olivertwistor.currencymgr.ui;

import nu.olivertwistor.currencymgr.database.CurrencyFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

public final class LoadFileAction extends AbstractAction
{
    private static final Logger LOG = LogManager.getLogger();

    private final GUI gui;

    public LoadFileAction(final GUI gui)
    {
        super("Load...");
        this.putValue(SHORT_DESCRIPTION, "Load an existing file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_L);

        this.gui = gui;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setDialogTitle("Load a currency manager file");

        final int status = fileChooser.showOpenDialog(this.gui);
        if (status == JFileChooser.APPROVE_OPTION)
        {
            final File loadedFile = fileChooser.getSelectedFile();
            final String fullPath = loadedFile.getAbsolutePath();

            final CurrencyFile currencyFile = new CurrencyFile(fullPath);
            try
            {
                currencyFile.load();
                this.gui.setCurrencyFile(currencyFile);
            }
            catch (final SQLException exception)
            {
                JOptionPane.showMessageDialog(
                        this.gui,
                        "The currency manager file couldn't be loaded.",
                        "Failed to load file",
                        JOptionPane.ERROR_MESSAGE,
                        null);
                LOG.fatal("Failed to load currency manager file.", exception);
            }
        }
    }
}
