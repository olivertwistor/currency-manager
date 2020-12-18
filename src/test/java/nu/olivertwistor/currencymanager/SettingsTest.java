package nu.olivertwistor.currencymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class SettingsTest
{
    private static Settings settings;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException
    {
        final URL url = SettingsTest.class.getResource("/app.properties");
        final File file = Paths.get(url.toURI()).toFile();

        settings = new Settings(file);
    }

    @Test
    void When_DbVersionIsRead_Then_NoExceptionIsThrown()
    {
        Assertions.assertDoesNotThrow(() -> settings.getDatabaseVersion());
    }

    @Test
    void When_DbVersionIsWritten_Then_SameValueCanBeRead()
    {
        final int expected = 10;
        settings.setDatabaseVersion(expected);
        final int actual = settings.getDatabaseVersion();

        Assertions.assertEquals(expected, actual);
    }
}
