package nu.olivertwistor.currencymgr.currency;

import nu.olivertwistor.currencymgr.database.Database;
import nu.olivertwistor.currencymgr.database.DatabaseUpgrader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CurrencyTest
{
    private static Database database;

    @BeforeAll
    private static void setUp() throws SQLException
    {
        database = new Database("currency-manager-test.sqlite");
        DatabaseUpgrader.upgrade(database, 1);

        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            final String sql = "DELETE FROM currency";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void testSaveNew() throws SQLException
    {
        final Currency currency = new Currency("EUR", "Euro");
        final boolean success = currency.save(database);

        assertThat(success, is(true));
    }

    @Test
    public void testSaveExisting() throws SQLException
    {
        final Currency currency = new Currency("SEK", "Svenska kronor");
        boolean success = currency.save(database);

        if (success)
        {
            currency.setName("Svensk krona");
            success = currency.save(database);
        }

        assertThat(success, is(true));
    }

    @Test
    public void testLoadMissing()
    {
        Assertions.assertThrows(NoSuchElementException.class, () ->
                Currency.load(database, 99));
    }

    @Test
    public void testLoadExisting() throws Exception
    {
        final Currency currency = new Currency("USD", "US Dollar");
        currency.save(database);

        final Currency currency1 = Currency.load(database, 1);
        Assertions.assertNotNull(currency1);
    }
}