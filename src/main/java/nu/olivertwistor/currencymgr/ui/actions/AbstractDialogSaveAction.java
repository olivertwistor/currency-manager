package nu.olivertwistor.currencymgr.ui.actions;

import nu.olivertwistor.currencymgr.database.CurrencyFile;
import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

/**
 * This abstract class provides a common {@link #actionPerformed(ActionEvent)}
 * where a save dialog opens. It doesn't set any of the usual
 * {@link AbstractAction} values, such as name, description, icon and mnemonic.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
abstract class AbstractDialogSaveAction extends AbstractAction
{
    private static final Logger LOG = LogManager.getLogger();

    private final GUI gui;

    /**
     * Creates a new abstract dialog save action.
     *
     * @param gui the gui of which the save dialog is a parent
     *
     * @since 0.1.0
     */
    AbstractDialogSaveAction(final GUI gui)
    {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setDialogTitle("Save currency file");

        final int status = fileChooser.showSaveDialog(this.gui);
        if (status == JFileChooser.APPROVE_OPTION)
        {
            final File createdFile = fileChooser.getSelectedFile();
            final String fullPath = createdFile.getAbsolutePath();

            final CurrencyFile currencyFile = new CurrencyFile(fullPath);
            try
            {
                currencyFile.save();
                this.gui.setCurrencyFile(currencyFile);
            }
            catch (final SQLException exception)
            {
                JOptionPane.showMessageDialog(
                        this.gui,
                        "The currency file couldn't be saved.",
                        "Failed to save file",
                        JOptionPane.ERROR_MESSAGE,
                        null);
                LOG.fatal("Failed to save currency file.", exception);
            }
        }
    }
}
