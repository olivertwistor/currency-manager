package nu.olivertwistor.currencymgr.ui.actions;

import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

/**
 * The save as file action is meant to be executed when the user wants to save
 * an open currency file to a new file. When it does, a save file dialog is
 * opened.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public final class SaveAsFileAction extends AbstractDialogSaveAction
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Creates a save as file action. Sets its name, short description,
     * mnemonic key and accelerator key.
     *
     * @param gui the gui of which the save dialog is a parent
     *
     * @since 0.1.0
     */
    public SaveAsFileAction(final GUI gui)
    {
        super(gui);
        this.putValue(NAME, "Save as...");
        this.putValue(SHORT_DESCRIPTION, "Save as file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                "control shift S"));

        LOG.debug("Created a SaveAsFileAction.");
    }
}
