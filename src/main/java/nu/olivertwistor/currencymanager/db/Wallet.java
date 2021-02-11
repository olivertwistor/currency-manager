package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A wallet is a currency pairs the user owns and trades with. It has a
 * describing name (optional), a base currency and a target currency. It
 * belongs to a portfolio.
 *
 * For example if the user trades buys bitcoin and sells to euros, bitcoin is
 * the target currency and euros is the base currency.
 *
 * @since 0.1.0
 */
@SuppressWarnings("StringConcatenation")
public class Wallet implements Dao<Wallet>, Serializable
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(Wallet.class);

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int baseCurrency;
    private int targetCurrency;
    private int portfolio;

    /**
     * Creates a wallet with a name, a base currency, target currency and the
     * portfolio of which this wallet belongs.
     *
     * @param name           description of this wallet
     * @param baseCurrency   ID of the {@link Currency} to use as a base
     * @param targetCurrency ID of the {@link Currency} to use as a target
     * @param portfolio      ID of the {@link Portfolio} of which this wallet
     *                       belongs
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Wallet(final String name,
                  final int baseCurrency,
                  final int targetCurrency,
                  final int portfolio)
    {
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.portfolio = portfolio;
    }

    /**
     * Creates a wallet with a name, a base currency, target currency and the
     * portfolio of which this wallet belongs.
     *
     * @param name           description of this wallet
     * @param baseCurrency   the {@link Currency} to use as a base
     * @param targetCurrency the {@link Currency} to use as a target
     * @param portfolio      the {@link Portfolio} of which this wallet belongs
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Wallet(final String name,
                  final Currency baseCurrency,
                  final Currency targetCurrency,
                  final Portfolio portfolio)
    {
        this(name, baseCurrency.getId(), targetCurrency.getId(),
                portfolio.getId());
    }

    /**
     * Creates a wallet with a name, a base currency, target currency and the
     * portfolio of which this wallet belongs.
     *
     * @param baseCurrency   ID of the {@link Currency} to use as a base
     * @param targetCurrency ID of the {@link Currency} to use as a target
     * @param portfolio      ID of the {@link Portfolio} of which this wallet
     *                       belongs
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Wallet(final int baseCurrency,
                  final int targetCurrency,
                  final int portfolio)
    {
        this("", baseCurrency, targetCurrency, portfolio);
    }

    /**
     * Creates a wallet with a name, a base currency, target currency and the
     * portfolio of which this wallet belongs.
     *
     * @param baseCurrency   the {@link Currency} to use as a base
     * @param targetCurrency the {@link Currency} to use as a target
     * @param portfolio      the {@link Portfolio} of which this wallet belongs
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Wallet(final Currency baseCurrency,
                  final Currency targetCurrency,
                  final Portfolio portfolio)
    {
        this(baseCurrency.getId(), targetCurrency.getId(), portfolio.getId());
    }

    @Override
    public int save(final Database database) throws SQLException
    {
        // If id > 0, we should update the record. If not, we should insert it
        // instead.
        if (this.id > 0)
        {
            @NonNls
            final String sql = "UPDATE wallet SET name = ?, " +
                    "base_currency = ?, target_currency = ?, portfolio = ? " +
                    "WHERE id = ?;";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.name);
                statement.setInt(2, this.baseCurrency);
                statement.setInt(3, this.targetCurrency);
                statement.setInt(4, this.portfolio);
                statement.setInt(5, this.id);

                statement.executeUpdate();
                LOG.info("Updated {} in the database.", this);
            }
        }
        else
        {
            @NonNls
            final String sql = "INSERT INTO wallet (name, base_currency, " +
                    "target_currency, portfolio) VALUES (?, ?, ?, ?);";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.name);
                statement.setInt(2, this.baseCurrency);
                statement.setInt(3, this.targetCurrency);
                statement.setInt(4, this.portfolio);

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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Wallet load(final int id, final Database database)
            throws SQLException
    {
        @NonNls
        final String sql = "SELECT name, base_currency, target_currency, " +
                "portfolio FROM wallet WHERE id = ?;";

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

                    @NonNls
                    final int targetCurrency =
                            resultSet.getInt("target_currency");

                    @NonNls
                    final int portfolio = resultSet.getInt("portfolio");

                    final Wallet wallet = new Wallet(
                            name, baseCurrency, targetCurrency, portfolio);
                    wallet.id = id;

                    LOG.info("Read {} from the database.", wallet);
                    return wallet;
                }

                // We didn't find a wallet with the given ID. Something's up.
                throw new NoSuchElementException(
                        "Failed to find a wallet with id " + id);
            }
        }
    }

    @SuppressWarnings("unused")
    @Override
    public List<Wallet> loadAll(final Database database) throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "SELECT id, name, base_currency, " +
                    "target_currency, portfolio FROM wallet;";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                final List<Wallet> wallets = new ArrayList<>();

                while (resultSet.next())
                {
                    @NonNls
                    final int id = resultSet.getInt("id");

                    @NonNls
                    final String name = resultSet.getString("name");

                    @NonNls
                    final int baseCurrency = resultSet.getInt("base_currency");

                    @NonNls
                    final int targetCurrency =
                            resultSet.getInt("target_currency");

                    @NonNls
                    final int portfolio = resultSet.getInt("portfolio");

                    final Wallet wallet = new Wallet(
                            name, baseCurrency, targetCurrency, portfolio);
                    wallet.id = id;

                    wallets.add(wallet);
                    LOG.debug("Read {} from the database.", wallet);
                }

                LOG.info("Read {} wallets from the database.", wallets.size());

                return wallets;
            }
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void delete(final Database database) throws SQLException
    {
        @NonNls
        final String sql = "DELETE FROM wallet WHERE id = ?;";

        try (final PreparedStatement statement =
                     database.getConnection().prepareStatement(sql))
        {
            statement.setInt(1, this.id);
            statement.executeUpdate();

            // Mark as deleted, so subsequent saves don't update, but inserts.
            this.id = 0;

            LOG.info("Deleted {} from the database.", this);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void deleteAll(final Database database) throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "DELETE FROM wallet;";

            statement.executeUpdate(sql);
            LOG.info("Deleted all wallets from the database.");

            // Mark this object as deleted, so subsequent saves don't update,
            // but inserts.
            this.id = 0;
        }
    }

    @Override
    public int count(final Database database) throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "SELECT COUNT(id) AS n FROM wallet;";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                @NonNls
                final int nRows = resultSet.getInt("n");

                LOG.debug("Found {} rows.", nRows);
                return nRows;
            }
        }
    }

    public int getId()
    {
        return this.id;
    }

    @SuppressWarnings("unused")
    public void setName(final String name)
    {
        this.name = name;
    }

    @SuppressWarnings("WeakerAccess")
    public void setBaseCurrency(final int baseCurrency)
    {
        this.baseCurrency = baseCurrency;
    }

    @SuppressWarnings("unused")
    public void setTargetCurrency(final int targetCurrency)
    {
        this.targetCurrency = targetCurrency;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Wallet{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", baseCurrency=" + this.baseCurrency +
                ", targetCurrency=" + this.targetCurrency +
                '}';
    }
}
