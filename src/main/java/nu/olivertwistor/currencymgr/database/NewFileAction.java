package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.currency.CurrencyDialog;
import nu.olivertwistor.currencymgr.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

/**
 * The new file action is meant to be executed when the user wants to create a
 * new currency file. When it does, a save file dialog is opened.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public final class NewFileAction extends AbstractDialogSaveAction
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Creates a new file action. Sets its name, short description, mnemonic
     * key and accelerator key.
     *
     * @param gui the gui of which the save dialog is a parent
     *
     * @since 0.1.0
     */
    public NewFileAction(final GUI gui)
    {
        super(gui);
        this.putValue(NAME, "New...");
        this.putValue(SHORT_DESCRIPTION, "Create a new file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));

        LOG.debug("Created a NewFileAction.");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);

        final CurrencyDialog dialog = new CurrencyDialog(this.gui);
        dialog.setVisible(true);

        try
        {
            this.gui.saveCurrencyFile();
        }
        catch (SQLException exception)
        {
            JOptionPane.showMessageDialog(
                    this.gui,
                    "The file couldn't be saved.",
                    "Save error",
                    JOptionPane.ERROR_MESSAGE);
            LOG.error("Failed to save currency file.", exception);
        }
    }
}
