package nu.olivertwistor.currencymanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class gives the {@link Currency} data model CRUD capabilities through
 * the {@link Dao} interface and a {@link Database}.
 *
 * @author Johan Nilsson
 * @since  0.1.0
 */
public class CurrencyDao implements Dao<Currency>
{
    private static final Logger log = LogManager.getLogger(CurrencyDao.class);

    private final Database database;

    /**
     * Creates a new instance of this class.
     *
     * @param database the database to use
     *
     * @since 0.1.0
     */
    public CurrencyDao(final Database database)
    {
        this.database = database;
    }

    @Override
    public Optional<Currency> get(final int id)
            throws SQLException, SQLTimeoutException
    {
        log.debug("Trying to get currency object with id {0}.", id);

        try (final Connection connection = this.database.getConnection())
        {
            final String sql = "SELECT id, name FROM currency WHERE id = ?";

            try (final PreparedStatement statement =
                         connection.prepareStatement(sql))
            {
                statement.setInt(1, id);

                try (final ResultSet resultSet = statement.executeQuery())
                {
                    // We're only expecting one result, so let's get just the
                    // first result.
                    if (resultSet.next())
                    {
                        final Currency currency = new Currency();
                        currency.setId(resultSet.getInt("id"));
                        currency.setName(resultSet.getString("name"));

                        log.debug("Found currency: {0}", currency);
                        return Optional.of(currency);
                    }

                    log.warn("Didn't find any currency objects with id {0}.",
                            id);
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Currency> getAll() throws SQLException
    {
        log.debug("Trying to get all currency objects.");

        final List<Currency> currencies = new ArrayList<>();

        try (final Connection connection = this.database.getConnection())
        {
            final String sql = "SELECT id, name FROM currency";

            try (final Statement statement = connection.createStatement())
            {
                try (final ResultSet resultSet = statement.executeQuery(sql))
                {
                    // SQLite closes the result set if it's empty, so we have
                    // to test for that prior to moving the row cursor.
                    if (!resultSet.isClosed())
                    {
                        while (resultSet.next())
                        {
                            final Currency currency = new Currency();
                            currency.setId(resultSet.getInt("id"));
                            currency.setName(resultSet.getString("name"));

                            log.debug("Found currency: {0}", currency);
                            currencies.add(currency);
                        }
                    }
                }
            }
        }

        return currencies;
    }

    @Override
    public void save(final Currency currency) throws SQLException
    {
        log.debug("Trying to save {0}.", currency);

        try (final Connection connection = this.database.getConnection())
        {
            final boolean alreadyExists;

            // To determine whether we should INSERT or UPDATE, we must first
            // determine whether the object to save already exists in the
            // database.
            final String existsSql = "SELECT id FROM currency WHERE id = ?";

            try (final PreparedStatement statement =
                         connection.prepareStatement(existsSql))
            {
                statement.setInt(1, currency.getId());

                try (final ResultSet resultSet = statement.executeQuery())
                {
                    alreadyExists = !resultSet.isClosed() && resultSet.next();

                    if (alreadyExists)
                    {
                        log.debug("{0} already exists in the database.",
                                currency);
                    }
                    else
                    {
                        log.debug("{0} doesn't exist in the database.",
                                currency);
                    }
                }
            }

            // Depending on alreadyExists, write the SQL as an UPDATE or an
            // INSERT.
            if (alreadyExists)
            {
                final String updateSql =
                        "UPDATE currency SET name = ? WHERE id = ?";

                try (final PreparedStatement statement =
                             connection.prepareStatement(updateSql))
                {
                    statement.setString(1, currency.getName());
                    statement.setInt(2, currency.getId());
                    statement.execute();

                    log.info("Updated {0} in the database.", currency);
                }
            }
            else
            {
                final String insertSql =
                        "INSERT INTO main.currency (id, name) VALUES (?, ?)";

                try (final PreparedStatement statement =
                             connection.prepareStatement(insertSql))
                {
                    statement.setInt(1, currency.getId());
                    statement.setString(2, currency.getName());
                    statement.execute();

                    log.info("Inserted {0} in the database.", currency);
                }
            }
        }
    }

    @Override
    public void delete(final Currency currency)
    {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String toString()
    {
        return "CurrencyDao{database=" + this.database + "}";
    }
}
