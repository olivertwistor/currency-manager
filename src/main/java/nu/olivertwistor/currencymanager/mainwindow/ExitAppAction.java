package nu.olivertwistor.currencymanager.mainwindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * This class defines an action for exiting the application.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
final class ExitAppAction extends AbstractAction
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(ExitAppAction.class);

    private static final long serialVersionUID = 406680657113993985L;

    /**
     * Creates a new action with a name, short description and a mnemonic.
     *
     * @since 0.1.0
     */
    ExitAppAction()
    {
        super("Exit the app");
        this.putValue(SHORT_DESCRIPTION, "This will exit the application.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_X);
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        LOG.info("Exiting the application.");
        System.exit(0);
    }
}
