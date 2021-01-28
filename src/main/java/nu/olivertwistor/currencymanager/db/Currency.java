package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class describes a currency with a ticker (short name, for example "EUR"
 * for euro, and "USD" for US dollar) and an optional description or name.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"unused", "StringConcatenation"})
public class Currency implements Dao<Currency>
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(Currency.class);

    private int id;
    private String ticker;
    private String description;

    /**
     * Creates a currency with a ticker and a description.
     *
     * @param ticker      short name, for example "EUR" for euro
     * @param description description of the currency, for example "Euro" for
     *                    EUR
     *
     * @since 0.1.0
     */
    public Currency(final String ticker, final String description)
    {
        this.ticker = ticker;
        this.description = description;
    }

    /**
     * Creates a currency with a ticker, but without description.
     *
     * @param ticker short name, for example "EUR" for euro
     *
     * @since 0.1.0
     */
    public Currency(final String ticker)
    {
        this(ticker, "");
    }

    @Override
    public int save(final Database database) throws SQLException
    {
        // If id > 0, we should update the record. If not, we should insert it
        // instead.
        if (this.id > 0)
        {
            @NonNls
            final String sql = "UPDATE currency SET ticker = ?, " +
                    "description = ? WHERE id = ?;";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.ticker);
                statement.setString(2, this.description);
                statement.setInt(3, this.id);

                statement.executeUpdate();
                LOG.info("Updated {} in the database.", this);
            }
        }
        else
        {
            @NonNls
            final String sql =
                    "INSERT INTO currency (ticker, description) VALUES (?, ?);";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.ticker);
                statement.setString(2, this.description);

                statement.executeUpdate();
                LOG.info("Inserted {} into the database.", this);

                // Get the last inserted row ID into this object.
                try (final ResultSet resultSet = statement.getGeneratedKeys())
                {
                    this.id = resultSet.getInt(1);
                    LOG.debug("Retrieved the row ID: {}", this.id);
                }
            }
        }

        return this.id;
    }

    @Override
    public Currency load(final int id, final Database database)
            throws SQLException
    {
        @NonNls
        final String sql =
                "SELECT ticker, description FROM currency WHERE id = ?;";

        try (final PreparedStatement statement =
                     database.getConnection().prepareStatement(sql))
        {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery())
            {
                final boolean hasNext = resultSet.next();
                if (hasNext)
                {
                    @NonNls
                    final String ticker = resultSet.getString("ticker");

                    @NonNls
                    final String description =
                            resultSet.getString("description");

                    final Currency currency = new Currency(ticker, description);
                    currency.id = id;

                    LOG.info("Read {} from the database.", currency);
                    return currency;
                }

                // We didn't find a currency with the given ID. Something's up.
                throw new NoSuchElementException(
                        "Failed to find a currency with id " + id);
            }
        }
    }

    @Override
    public List<Currency> loadAll(final Database database) throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "SELECT id, ticker, description FROM currency;";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                final List<Currency> currencies = new ArrayList<>();

                while (resultSet.next())
                {
                    @NonNls
                    final int id = resultSet.getInt("id");

                    @NonNls
                    final String ticker = resultSet.getString("ticker");

                    @NonNls
                    final String description =
                            resultSet.getString("description");

                    final Currency currency = new Currency(ticker, description);
                    currency.id = id;

                    currencies.add(currency);
                    LOG.debug("Read {} from the database.", currency);
                }

                LOG.info("Read {} currencies from the database.",
                        currencies.size());

                return currencies;
            }
        }

    }

    @Override
    public void delete(final Database database) throws SQLException
    {
        @NonNls
        final String sql = "DELETE FROM currency WHERE id = ?;";

        try (final PreparedStatement statement =
                     database.getConnection().prepareStatement(sql))
        {
            statement.setInt(1, this.id);
            statement.executeUpdate();

            LOG.info("Deleted {} from the database.", this);
        }
    }

    @Override
    public void deleteAll(final Database database) throws SQLException
    {

        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "DELETE FROM currency;";

            statement.executeUpdate(sql);
            LOG.info("Deleted all currencies from the database.");
        }
    }

    public int getId()
    {
        return this.id;
    }

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                @NonNls
    }

    int getId()
    {
        return this.id;
    }

    public String getTicker()
    {
        return this.ticker;
    }

    public void setTicker(final String ticker)
    {
        this.ticker = ticker;
    }

    public String getDescription()
    {
        return this.description;
    }

    void setDescription(final String description)
    {
        this.description = description;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Currency{" +
                "id=" + this.id +
                ", ticker='" + this.ticker + '\'' +
                ", description='" + this.description + '\'' +
                '}';
    }
}
