package nu.olivertwistor.currencymanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * This class provides read and write access to the application config.
 *
 * @since 0.1.0
 */
public final class AppConfig implements Serializable
{
    @SuppressWarnings("unused")
    @NonNls
    private static final Logger LOG = LogManager.getLogger(AppConfig.class);

    private static final long serialVersionUID = 1L;

    @NonNls
    private final Properties properties;

    /**
     * Loads an application config file from the provided path. If there is no
     * application config file at that location, it will be created and
     * prefilled with default values.
     *
     * @param path path to either an existing config file, or where a new one
     *             should be created
     *
     * @throws IOException if writing to the file failed.
     *
     * @since 0.1.0
     */
    public AppConfig(final Path path) throws IOException
    {
        this.properties = new Properties();

        // Determine whether there is a file at the provided path. If it is,
        // load it. Otherwise, create a new file prefilled with default values.
        final File file = path.toFile();
        if (file.exists())
        {
            try (final InputStream inputStream = new FileInputStream(file))
            {
                this.properties.load(inputStream);
            }
        }
        else
        {
            Files.createDirectories(path.getParent());
            file.createNewFile();
            try (final OutputStream outputStream = new FileOutputStream(file))
            {
                // Here all properties should be set and written to the file.
                //properties.setProperty("abc", "def");
                this.properties.store(outputStream, "App config");
            }
        }
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
