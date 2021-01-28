package nu.olivertwistor.currencymanager.util;

import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;

/**
 * Unit tests for the {@link AppConfig} class.
 *
 * @since 0.1.0
 */
public class AppConfigTest
{
    private static final @NonNls String FILE_NAME = "/app.properties";

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
        new AppConfig(FILE_NAME);
    }

    /**
     * Asserts that when loading the window size config property, the correct
     * value is returned.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void GetWindowSize_OnFixedDimension_CorrectValueReturned()
            throws Exception
    {
        final AppConfig appConfig = new AppConfig(FILE_NAME);

        final Dimension expected = new Dimension(800, 600);
        final Dimension actual = appConfig.getWindowSize();

        Assertions.assertEquals(expected, actual);
    }
}
