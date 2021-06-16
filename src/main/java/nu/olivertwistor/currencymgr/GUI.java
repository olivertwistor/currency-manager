package nu.olivertwistor.currencymgr;

import nu.olivertwistor.currencymgr.database.CurrencyFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

/**
 * The GUI class is the topmost class responsible for handling the GUI. It
 * creates a JFrame and all the components required for the GUI.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public final class GUI extends JFrame
{
    private static final Logger LOG = LogManager.getLogger();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @SuppressWarnings("unused")
    private transient CurrencyFile currencyFile;

    /**
     * Creates a new GUI object (a JFrame), with a title and a size. Sets the
     * close behaviour to exit the app.
     *
     * @param title which title the GUI shows in its title bar
     *
     * @since 0.1.0
     */
    public GUI(final String title)
    {
        super(title);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        LOG.info("Created a GUI of size {}x{}.", WIDTH, HEIGHT);
    }

    /**
     * Creates an instance of {@link MainMenuBar} and sets it as the JMenuBar
     * of this GUI.
     *
     * @since 0.1.0
     */
    public void attachMainMenuBar()
    {
        final JMenuBar menuBar = new MainMenuBar(this);
        this.setJMenuBar(menuBar);

        LOG.debug("Attached a menu bar.");
    }

    public CurrencyFile getCurrencyFile()
    {
        return this.currencyFile;
    }

    public void setCurrencyFile(final CurrencyFile currencyFile)
    {
        this.currencyFile = currencyFile;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "GUI{" +
                "currencyFile=" + this.currencyFile +
                '}';
    }
}
