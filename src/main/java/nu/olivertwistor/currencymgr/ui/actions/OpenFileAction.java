package nu.olivertwistor.currencymgr.ui.actions;

import nu.olivertwistor.currencymgr.database.CurrencyFile;
import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

/**
 * Action that fires when the user wants to load a currency file.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public final class OpenFileAction extends AbstractAction
{
    private static final Logger LOG = LogManager.getLogger();

    private final GUI gui;

    /**
     * Creates an open file action. Sets its name, short description, mnemonic
     * key and accelerator key.
     *
     * @param gui the gui of which the open dialog is a parent
     *
     * @since 0.1.0
     */
    public OpenFileAction(final GUI gui)
    {
        super("Open...");
        this.putValue(SHORT_DESCRIPTION, "Open an existing file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

        this.gui = gui;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setDialogTitle("Open a currency file");

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
                        "The currency file couldn't be opened.",
                        "Failed to open file",
                        JOptionPane.ERROR_MESSAGE,
                        null);
                LOG.fatal("Failed to open currency file.", exception);
            }
        }
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "OpenFileAction{" +
                "gui=" + this.gui +
                '}';
    }
}
