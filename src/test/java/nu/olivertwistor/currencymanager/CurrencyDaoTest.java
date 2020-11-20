package nu.olivertwistor.currencymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class CurrencyDaoTest
{
    private static Database database;
    private static Dao<Currency> currencyDao;

    @SuppressWarnings("SqlWithoutWhere")
    @BeforeAll
    static void initDatabase()
            throws FileNotFoundException, SQLException, URISyntaxException
    {
        database = new Database("/test.sqlite3");
        currencyDao = new CurrencyDao(database);

        try (final Connection connection = database.getConnection())
        {
            // First, truncate the currency table.
            try (final Statement statement = connection.createStatement())
            {
                statement.execute("DELETE FROM main.currency");
            }

            // Then, add some records for the rest of the tests.
            try (final PreparedStatement statement =
                         connection.prepareStatement(
                                 "INSERT INTO main.currency (id, name) " +
                                         "VALUES (?, ?)"))
            {
                statement.setInt(1, 1);
                statement.setString(2, "USD");
                statement.execute();

                statement.setInt(1, 2);
                statement.setString(2, "SEK");
                statement.execute();

                statement.setInt(1, 3);
                statement.setString(2, "GBP");
                statement.execute();
            }
        }
    }

    @Test
    public void When_GettingCurrencyId2_Then_CurrencyId2IsReturned()
            throws SQLException
    {
        final Optional<Currency> currencyOptional = currencyDao.get(2);
        final Currency currency = currencyOptional.get();

        Assertions.assertEquals("SEK", currency.getName());
    }

    @Test
    public void When_GettingAll_Then_ThreeRecordsAreReturned()
            throws SQLException
    {
        final List<Currency> currencies = currencyDao.getAll();
        Assertions.assertEquals(3, currencies.size());
    }

    @Test
    public void When_ExistingCurrencyIsSaved_Then_ItGetsUpdatedInDatabase()
            throws SQLException
    {
        final Currency currency = new Currency(1, "DKK");
        currencyDao.save(currency);

        // Let's retrieve the currency with ID 1. Its name should be "DKK" and
        // not "USD" as it was originally.
        final Optional<Currency> dbCurrency = currencyDao.get(1);
        Assertions.assertEquals("DKK", dbCurrency.get().getName());
    }

    @Test
    public void When_NewCurrencyIsSaved_Then_ItGetsInsertedInDatabase()
            throws SQLException
    {
        final Currency currency = new Currency(4, "BTC");
        currencyDao.save(currency);

        // Let's retrieve the currency with ID 4. It should exist.
        final Optional<Currency> dbCurrency = currencyDao.get(4);
        Assertions.assertNotEquals(Optional.empty(), dbCurrency);
    }
}
