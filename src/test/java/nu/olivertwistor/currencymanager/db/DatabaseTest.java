package nu.olivertwistor.currencymanager.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * Unit tests for the {@link Database} class.
 *
 * @since 0.1.0
 */
public class DatabaseTest
{
    /**
     * Asserts that constructing a new Database object does not throw an
     * {@link SQLException}.
     *
     * @since 0.1.0
     */
    @Test
    public void Database_Construction_DoesNotThrowException()
    {
        Assertions.assertThrows(SQLException.class, () ->
                new Database("test.sqlite"));
    }
}
