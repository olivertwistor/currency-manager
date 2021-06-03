package nu.olivertwistor.currencymgr.ui;

import nu.olivertwistor.currencymgr.database.CurrencyFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

public final class GUI extends JFrame
{
    private static final Logger LOG = LogManager.getLogger();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private CurrencyFile currencyFile;

    public GUI(final String title)
    {
        super(title);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void attachMainMenuBar()
    {
        final JMenuBar menuBar = new MainMenuBar(this);
        this.setJMenuBar(menuBar);
    }

    public void setCurrencyFile(final CurrencyFile currencyFile)
    {
        this.currencyFile = currencyFile;
    }
}
