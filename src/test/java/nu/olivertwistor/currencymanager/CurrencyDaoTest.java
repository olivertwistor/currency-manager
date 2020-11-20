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
import java.util.Currency;
import java.util.Optional;

public class CurrencyDaoTest
{
    private static Database database;

    @SuppressWarnings("SqlWithoutWhere")
    @BeforeAll
    static void initDatabase()
            throws FileNotFoundException, SQLException, URISyntaxException
    {
        database = new Database("/test.sqlite3");

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
    public void When_GettingCurrencyId2_Then_CurrencyId2IsRetrieved()
            throws SQLException
    {
        final Dao<Currency> currencyDao = new CurrencyDao(database);

        final Optional<Currency> currencyOptional = currencyDao.get(2);
        final Currency currency = currencyOptional.get();

        Assertions.assertEquals("SEK", currency.getName());
    }
}
