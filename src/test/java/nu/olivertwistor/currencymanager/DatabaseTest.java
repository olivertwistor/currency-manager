package nu.olivertwistor.currencymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

/**
 * Unit tests for the {@link Database} class.
 *
 * @since  0.1.0
 */
final class DatabaseTest
{
    /**
     * Asserts that when connecting to an existing database, no exception is
     * thrown.
     *
     * @throws Exception if something went wrong
     *
     * @since 0.1.0
     */
    @Test
    void When_ConnectingToExistingDatabase_Then_NoExceptionIsThrown()
            throws Exception
    {
        new Database("/test.sqlite3");
    }

    /**
     * Asserts that when connecting to a nonexisting database, an exception is
     * thrown.
     *
     * @since 0.1.0
     */
    @Test
    void When_ConnectingToNonExistingDatabase_Then_ExceptionIsThrown()
    {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> new Database("/no_db"));
    }
}
