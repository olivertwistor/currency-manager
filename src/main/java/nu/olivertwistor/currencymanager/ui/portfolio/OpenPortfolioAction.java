package nu.olivertwistor.currencymanager.ui.portfolio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OpenPortfolioAction extends AbstractAction
{
    @NonNls
    private static final Logger LOG =
            LogManager.getLogger(OpenPortfolioAction.class);

    public OpenPortfolioAction()
    {
        super("Open portfolio");
        this.putValue(SHORT_DESCRIPTION, "Open a portfolio.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_O);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        LOG.debug("Open portfolio action invoked.");
    }
}
