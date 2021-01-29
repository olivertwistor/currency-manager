package nu.olivertwistor.currencymanager;

import nu.olivertwistor.currencymanager.ui.MainWindow;
import nu.olivertwistor.currencymanager.util.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.SwingUtilities;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main entry point for this application.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
final class App
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(App.class);

    /**
     * Creates config objects and starts the main program window.
     *
     * @param args not used
     *
     * @since 0.1.0
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                final MainWindow mainWindow =
                        new MainWindow("Currency Manager");
                mainWindow.useMainMenuBar();
                mainWindow.setVisible(true);

                // Load or create app config in user home folder. Let
                // MainWindow access it.
                final Path appConfigPath = Paths.get(
                        System.getProperty("user.home"),
                        ".olivertwistor", ".cm", "app.properties");
                try
                {
                    final AppConfig appConfig = new AppConfig(appConfigPath);
                    mainWindow.setAppConfig(appConfig);
                }
                catch (final IOException e)
                {
                    LOG.error("x", e);
                }
            }
        });
    }
}
