package nu.olivertwistor.currencymanager.util;

import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit tests for the {@link AppConfig} class.
 *
 * @since 0.1.0
 */
public class AppConfigTest
{
    private static final @NonNls Path PROP = Paths.get("test.properties");

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
        new AppConfig(PROP);
    }
}
