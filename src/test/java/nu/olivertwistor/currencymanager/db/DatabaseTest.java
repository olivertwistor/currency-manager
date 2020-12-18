package nu.olivertwistor.currencymanager.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * Unit tests for the {@link Database} class.
 *
 * @since  0.1.0
 */
final class DatabaseTest
{
    private static Database existing_db;

    @BeforeAll
    static void setUp() throws URISyntaxException, FileNotFoundException
    {
        final URL existingDbResource =
                DatabaseTest.class.getResource("/test.sqlite3");
        final File existingDbFile =
                Paths.get(existingDbResource.toURI()).toFile();
        final String existingDbPath = existingDbFile.getAbsolutePath();

        existing_db = new Database(existingDbPath);
    }
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
            throws FileNotFoundException

    {
        new Database("test.sqlite3");
    }

    @Test
    void When_ReadingDbVersion_Then_CorrectVersionIsFound() throws SQLException
    {
        final int expectedVersion = 99;
        final String expectedDate = "2099-12-31";

        final int actualVersion = existing_db.readCurrentDbVersion();

        Assertions.assertEquals(expectedVersion, actualVersion);
    }
}
