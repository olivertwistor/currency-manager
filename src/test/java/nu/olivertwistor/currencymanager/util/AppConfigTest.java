package nu.olivertwistor.currencymanager.util;

import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit tests for the {@link AppConfig} class.
 *
 * @since 0.1.0
 */
public class AppConfigTest
{
    @NonNls
    private static Path prop;

    @BeforeAll
    public static void setUp() throws Exception
    {
        final URL url = AppConfigTest.class.getResource("/test.properties");
        final URI uri = url.toURI();
        prop = Paths.get(uri);
    }

    /**
     * Asserts that when creating a new instance of the {@link AppConfig}
     * class, no exception is thrown.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void AppConfig_Constructor_NoExceptionIsThrown() throws Exception
    {
        new AppConfig(prop);
    }
}
