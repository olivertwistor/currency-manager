package nu.olivertwistor.currencymanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class provides read and write access to the application config.
 *
 * @since 0.1.0
 */
public final class AppConfig
{
    private static final @NonNls Logger LOG = LogManager.getLogger(
            AppConfig.class);

    private final Properties properties;

    /**
     * Loads an application config file from resources.
     *
     * @param filename path to the config file to load
     *
     * @throws FileNotFoundException if the specified config file couldn't be
     *                               found.
     * @throws IOException           if loading or parsing the config file
     *                               failed.
     *
     * @since 0.1.0
     */
    public AppConfig(final String filename)
            throws FileNotFoundException, IOException
    {
        this.properties = new Properties();

        try (final InputStream inputStream =
                     this.getClass().getResourceAsStream(filename))
        {
            if (inputStream == null)
            {
                throw new FileNotFoundException("App config file not found.");
            }
            this.properties.load(inputStream);

            LOG.info("Application config successfully loaded.");
        }
        catch (final IllegalArgumentException e)
        {
            LOG.error("Failed to parse app config file.", e);
            throw new IOException(e);
        }
    }

    private int getWindowWidth()
    {
        final String rawValue = this.properties.getProperty("app.window.width");
        return Integer.parseInt(rawValue);
    }

    private int getWindowHeight()
    {
        final String rawValue = this.properties.getProperty(
                "app.window.height");
        return Integer.parseInt(rawValue);
    }

    public Dimension getWindowSize()
    {
        final int width = this.getWindowWidth();
        final int height = this.getWindowHeight();

        final Dimension size = new Dimension(width, height);
        LOG.debug("Window size: {}", size);

        return size;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "AppConfig{" +
                "properties=" + this.properties +
                '}';
    }
}
