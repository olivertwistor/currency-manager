package nu.olivertwistor.currencymanager.util;

import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;

public class AppConfigTest
{
    private static final @NonNls String FILE_NAME = "/app.properties";

    @Test
    public void When_CreatingAppConfigObject_Then_NoExceptionIsThrown()
            throws Exception
    {
        new AppConfig(FILE_NAME);
    }

    @Test
    public void When_LoadingWindowSize_Then_CorrectValueIsReturned()
            throws Exception
    {
        final AppConfig appConfig = new AppConfig(FILE_NAME);

        final Dimension expected = new Dimension(800, 600);
        final Dimension actual = appConfig.getWindowSize();

        Assertions.assertEquals(expected, actual);
    }
}
