package nu.olivertwistor.currencymgr.ui.actions;

import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

@SuppressWarnings("HardCodedStringLiteral")
public final class SaveAsFileAction extends AbstractDialogSaveAction
{
    private static final Logger LOG = LogManager.getLogger();

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
