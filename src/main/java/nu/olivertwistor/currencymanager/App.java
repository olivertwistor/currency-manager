package nu.olivertwistor.currencymanager;

import nu.olivertwistor.currencymanager.mainwindow.MainWindow;
import nu.olivertwistor.currencymanager.util.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        final AppConfig appConfig;
        try
        {
            appConfig = new AppConfig("/app.properties");
        }
        catch (final FileNotFoundException e)
        {
            LOG.fatal("Failed to find app config file.", e);
            return;
        }
        catch (final IOException e)
        {
            LOG.fatal("Failed to create app config object.", e);
            return;
        }

        final Component mainWindow = new MainWindow(
                "Currency Manager", appConfig.getWindowSize());
        mainWindow.setVisible(true);
    }
}
