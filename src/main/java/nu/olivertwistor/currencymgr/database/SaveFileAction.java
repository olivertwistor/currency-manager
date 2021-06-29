package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The save file action is meant to be executed when the user wants to save an
 * open currency file.
 *
 * Currently, the action is not implemented.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"HardCodedStringLiteral", "ClassOnlyUsedInOnePackage"})
public final class SaveFileAction extends AbstractAction
{
    private static final Logger LOG = LogManager.getLogger();

    @SuppressWarnings("unused")
    private final GUI gui;

    /**
     * Creates a save file action. Sets its name, short description, mnemonic
     * key and accelerator key.
     *
     * @param gui the gui of which the save dialog is a parent
     *
     * @since 0.1.0
     */
    public SaveFileAction(final GUI gui)
    {
        super("Save");
        this.putValue(SHORT_DESCRIPTION, "Save current file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

        this.gui = gui;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        LOG.error("not yet implemented");
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "SaveFileAction{" +
                "gui=" + this.gui +
                '}';
    }
}
