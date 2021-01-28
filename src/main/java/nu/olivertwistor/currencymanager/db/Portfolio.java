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
 * The portfolio is the overarching set of currency pairs the user owns and
 * trades with. It has a describing name and a base currency, as which every
 * currency pair ultimately is expressed after conversion.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"unused", "StringConcatenation"})
public class Portfolio implements Dao<Portfolio>
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(Portfolio.class);

    private int id;
    private String name;
    private int baseCurrency;

    /**
     * Creates a portfolio with a name and a base currency.
     *
     * @param name         description of this portfolio
     * @param baseCurrency ID of the {@link Currency} to use as a base
     *
     * @since 0.1.0
     */
    public Portfolio(final String name, final int baseCurrency)
    {
        this.name = name;
        this.baseCurrency = baseCurrency;
    }

    /**
     * Creates a portfolio with a name and a base currency.
     *
     * @param name         description of this portfolio
     * @param baseCurrency the {@link Currency} to use as a base
     *
     * @since 0.1.0
     */
    public Portfolio(final String name, final Currency baseCurrency)
    {
        this(name, baseCurrency.getId());
    }

    @Override
    public int save(final Database database) throws SQLException
    {
        // If id > 0, we should update the record. If not, we should insert it
        // instead.
        if (this.id > 0)
        {
            @NonNls
            final String sql = "UPDATE portfolio SET name = ?, " +
                    "base_currency = ? WHERE id = ?;";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.name);
                statement.setInt(2, this.baseCurrency);
                statement.setInt(3, this.id);

                statement.executeUpdate();
                LOG.info("Updated {} in the database.", this);
            }
        }
        else
        {
            @NonNls
            final String sql = "INSERT INTO portfolio (name, base_currency) " +
                    "VALUES (?, ?);";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.name);
                statement.setInt(2, this.baseCurrency);

                statement.executeUpdate();
                LOG.info("Inserted {} in the database.", this);

                // Get the last inserted row ID into this object.
                try (final ResultSet resultSet = statement.getGeneratedKeys())
                {
                    this.id = resultSet.getInt(1);
                }
            }
        }

        return this.id;
    }

    @Override
    public Portfolio load(final int id, final Database database)
            throws SQLException
    {
        @NonNls
        final String sql =
                "SELECT name, base_currency FROM portfolio WHERE id = ?;";

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
                    final String name = resultSet.getString("name");

                    @NonNls
                    final int baseCurrency = resultSet.getInt("base_currency");

                    final Portfolio portfolio =
                            new Portfolio(name, baseCurrency);
                    portfolio.id = id;

                    LOG.info("Read {} from the database.", portfolio);
                    return portfolio;
                }

                // We didn't find a portfolio with the given ID. Something's up.
                throw new NoSuchElementException(
                        "Failed to find a portfolio with id " + id);
            }
        }
    }

    @Override
    public List<Portfolio> loadAll(final Database database) throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "SELECT name, base_currency FROM portfolio;";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                final List<Portfolio> portfolios = new ArrayList<>();

                while (resultSet.next())
                {
                    @NonNls
                    final int id = resultSet.getInt("id");

                    @NonNls
                    final String name = resultSet.getString("name");

                    @NonNls
                    final int baseCurrency = resultSet.getInt("base_currency");

                    final Portfolio portfolio =
                            new Portfolio(name, baseCurrency);
                    portfolio.id = id;

                    portfolios.add(portfolio);
                    LOG.debug("Read {} from the database.", portfolio);
                }

                LOG.info("Read {} portfolios from the database.",
                        portfolios.size());

                return portfolios;
            }
        }

    }

    @Override
    public void delete(final Database database) throws SQLException
    {
        @NonNls
        final String sql = "DELETE FROM portfolio WHERE id = ?;";

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
            final String sql = "DELETE FROM portfolio;";

            statement.executeUpdate(sql);
            LOG.info("Deleted all portfolios from the database.");
        }
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getBaseCurrency()
    {
        return this.baseCurrency;
    }

    public void setBaseCurrency(final int baseCurrency)
    {
        this.baseCurrency = baseCurrency;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Portfolio{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", baseCurrency=" + this.baseCurrency +
                '}';
    }
}
