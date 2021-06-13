package nu.olivertwistor.currencymgr.ui.actions;

import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@SuppressWarnings({"HardCodedStringLiteral", "ClassOnlyUsedInOnePackage"})
public final class SaveFileAction extends AbstractAction
{
    private static final Logger LOG = LogManager.getLogger();

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final GUI gui;

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
}
