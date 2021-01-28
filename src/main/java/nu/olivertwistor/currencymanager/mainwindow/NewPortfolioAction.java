package nu.olivertwistor.currencymanager.mainwindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewPortfolioAction extends AbstractAction
{
    @NonNls
    private static final Logger LOG =
            LogManager.getLogger(NewPortfolioAction.class);

    NewPortfolioAction()
    {
        super("New portfolio");
        this.putValue(SHORT_DESCRIPTION, "Create a new portfolio.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_P);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        LOG.info("New portfolio action invoked.");
    }
}
