package nu.olivertwistor.currencymanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class Settings
{
    private static final Logger log = LogManager.getLogger(Settings.class);

    private static final String param_database_version = "database-version";

    private final Properties properties;
    private final File settingsFile;

    public Settings(final File file) throws FileNotFoundException, IOException
    {
        this.properties = new Properties();
        this.settingsFile = file;
        try (final InputStream inputStream =
                     new FileInputStream(this.settingsFile))
        {
            this.properties.load(inputStream);
        }
    }

    public int getDatabaseVersion() throws NoSuchElementException
    {
        return this.readInt(param_database_version);
    }

    public void setDatabaseVersion(final int databaseVersion)
    {
        this.writeInt(param_database_version, databaseVersion);
    }

    private String readString(final String property)
            throws NoSuchElementException
    {
        final String value = this.properties.getProperty(property);
        if (value == null)
        {
            throw new NoSuchElementException(
                    "Failed to find property " + property);
        }

        return value;
    }

    private int readInt(final String property) throws NoSuchElementException
    {
        final String rawValue = this.readString(property);
        try
        {
            final int value = Integer.parseInt(rawValue);
            return value;
        }
        catch (final NumberFormatException e)
        {
            throw new NoSuchElementException(property + " exists, but can't " +
                    "be converted to an integer.");
        }
    }

    private void writeString(final String property, final String value)
    {
        try (final OutputStream outputStream =
                     new FileOutputStream(this.settingsFile))
        {
            this.properties.setProperty(property, value);
            this.properties.store(outputStream, null);
        }
        catch (final FileNotFoundException e)
        {
            log.error("Failed to find the settings file {0}.",
                    this.settingsFile.getAbsolutePath());
        }
        catch (final IOException e)
        {
            log.error("Failed to write the property {0}={1} to the settings " +
                    "file {2}.",
                    property, value, this.settingsFile.getAbsolutePath());
        }
    }

    private void writeInt(final String property, final int value)
    {
        final String valueAsString = String.valueOf(value);

        this.writeString(property, valueAsString);
    }
}
