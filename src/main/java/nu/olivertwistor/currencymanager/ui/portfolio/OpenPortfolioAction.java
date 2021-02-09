package nu.olivertwistor.currencymanager.ui.portfolio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The action "the user wants to open an existing database file".
 *
 * @since 0.1.0
 */
@SuppressWarnings({"HardCodedStringLiteral", "unused"})
public final class OpenPortfolioAction extends AbstractAction
{
    @NonNls
    private static final Logger LOG =
            LogManager.getLogger(OpenPortfolioAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link Action} for the user opening an existing
     * database. This adds a name, description and a mnemonic key.
     *
     * @since 0.1.0
     */
    public OpenPortfolioAction()
    {
        super("Open portfolio");
        this.putValue(SHORT_DESCRIPTION, "Open a portfolio.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_O);
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        LOG.debug("Open portfolio action invoked.");

        throw new UnsupportedOperationException("not implemented");
    }
}
