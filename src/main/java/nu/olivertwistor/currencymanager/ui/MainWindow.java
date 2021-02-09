package nu.olivertwistor.currencymanager.ui;

import nu.olivertwistor.currencymanager.db.Database;
import nu.olivertwistor.currencymanager.util.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Main window for Currency Manager. When the app starts, this window will be
 * shown. When the user closes this window, the app will exit.
 *
 * @since 0.1.0
 */
public final class MainWindow extends JFrame
{
    @SuppressWarnings("unused")
    @NonNls
    private static final Logger LOG = LogManager.getLogger(MainWindow.class);

    private static final long serialVersionUID = 7830866209130181043L;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private AppConfig appConfig;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private transient Database database;

    /**
     * Creates the window, sets its title, adds the main menu bar and all the
     * visual components of the window.
     *
     * @param title window title
     *
     * @since 0.1.0
     */
    public MainWindow(final @Nls String title)
    {
        super(title);

        // When this JFrame closes, the app should exit.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a JPanel as the content pane.
        final Container contentPanel = new JPanel(new BorderLayout());
        this.setContentPane(contentPanel);

        this.setLocationByPlatform(true);
    }

    /**
     * Sets an instance of {@link MainMenuBar} as the JMenuBar to use.
     *
     * @since 0.1.0
     */
    public void useMainMenuBar()
    {
        final JMenuBar mainMenuBar = new MainMenuBar(this);
        this.setJMenuBar(mainMenuBar);

        LOG.debug("Set {} as JMenuBar.", mainMenuBar);
    }

    public void setAppConfig(final AppConfig appConfig)
    {
        this.appConfig = appConfig;
    }

    public void setDatabase(final Database database)
    {
        this.database = database;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "MainWindow{" +
                "appConfig=" + this.appConfig +
                ", database=" + this.database +
                '}';
    }
}
