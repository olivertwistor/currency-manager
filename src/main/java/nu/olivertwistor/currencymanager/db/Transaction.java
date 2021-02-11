package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A transaction is one instance of buying or selling currency. It has a date
 * and two pairs of amounts and currencies, as well as a wallet in which this
 * transaction is made. One pair of amounts and currencies describe what's
 * bought or sold; the other pair describe with what.
 *
 * For example, if 10 USD is bought by 100 SEK, one pair would be [10, USD] and
 * the other would be [-100, SEK].
 *
 * @since 0.1.0
 */
@SuppressWarnings("StringConcatenation")
public class Transaction implements Dao<Transaction>, Serializable
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(Transaction.class);

    private static final long serialVersionUID = 1L;

    private int id;
    private String date;
    private double toAmount;
    private int toCurrency;
    private double fromAmount;
    private int fromCurrency;
    private int wallet;

    /**
     * Creates a transaction with a date, two pairs of amounts and currencies,
     * and a wallet in which this transaction is made.
     *
     * @param date         date of the transaction
     * @param toAmount     the amount of currency that was bought or sold
     * @param toCurrency   ID of the {@link Currency} that was bought or sold
     * @param fromAmount   the amount of currency that was used to buy or sell
     * @param fromCurrency ID of the {@link Currency} that was used to buy or
     *                     sell
     * @param wallet       ID of the {@link Wallet} in which this transaction
     *                     is made
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Transaction(final LocalDate date,
                       final double toAmount,
                       final int toCurrency,
                       final double fromAmount,
                       final int fromCurrency,
                       final int wallet)
    {
        this.date = Database.toSQLiteDate(date);
        this.toAmount = toAmount;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.fromCurrency = fromCurrency;
        this.wallet = wallet;
    }

    /**
     * Creates a transaction with a date, two pairs of amounts and currencies,
     * and a wallet in which this transaction is made.
     *
     * @param date         date of the transaction
     * @param toAmount     the amount of currency that was bought or sold
     * @param toCurrency   the {@link Currency} that was bought or sold
     * @param fromAmount   the amount of currency that was used to buy or sell
     * @param fromCurrency the {@link Currency} that was used to buy or sell
     * @param wallet       the {@link Wallet} in which this transaction was made
     *
     * @since 0.1.0
     */
    @SuppressWarnings("WeakerAccess")
    public Transaction(final LocalDate date,
                       final double toAmount,
                       final Currency toCurrency,
                       final double fromAmount,
                       final Currency fromCurrency,
                       final Wallet wallet)
    {
        this(date, toAmount, toCurrency.getId(), fromAmount,
                fromCurrency.getId(), wallet.getId());
    }

    @Override
    public int save(final Database database) throws SQLException
    {
        // If id > 0, we should update the record. If not, we should insert it
        // instead.
        if (this.id > 0)
        {
            @NonNls
            final String sql = "UPDATE 'transaction' SET date = ?, " +
                    "to_amount = ?, to_currency = ?, from_amount = ?, " +
                    "from_currency = ?, wallet = ? WHERE id = ?;";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.date);
                statement.setDouble(2, this.toAmount);
                statement.setInt(3, this.toCurrency);
                statement.setDouble(4, this.fromAmount);
                statement.setInt(5, this.fromCurrency);
                statement.setInt(6, this.wallet);
                statement.setInt(7, this.id);

                statement.executeUpdate();
                LOG.info("Updated {} in the database.", this);
            }
        }
        else
        {
            @NonNls
            final String sql = "INSERT INTO 'transaction' (date, to_amount, " +
                    "to_currency, from_amount, from_currency, wallet) VALUES " +
                    "(?, ?, ?, ?, ?, ?);";

            try (final PreparedStatement statement =
                         database.getConnection().prepareStatement(sql))
            {
                statement.setString(1, this.date);
                statement.setDouble(2, this.toAmount);
                statement.setInt(3, this.toCurrency);
                statement.setDouble(4, this.fromAmount);
                statement.setInt(5, this.fromCurrency);
                statement.setInt(6, this.wallet);

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
    public Transaction load(final int id, final Database database)
            throws SQLException
    {
        @NonNls
        final String sql = "SELECT date, to_amount, to_currency, from_amount, from_currency, wallet FROM 'transaction' WHERE id = ?;";

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
                    final String date = resultSet.getString("date");

                    @NonNls
                    final double toAmount = resultSet.getDouble("to_amount");

                    @NonNls
                    final int toCurrency = resultSet.getInt("to_currency");

                    @NonNls
                    final double fromAmount =
                            resultSet.getDouble("from_amount");

                    @NonNls
                    final int fromCurrency = resultSet.getInt("from_currency");

                    @NonNls
                    final int wallet = resultSet.getInt("wallet");

                    final Transaction transaction = new Transaction(
                            Database.toLocalDate(date),
                            toAmount,
                            toCurrency,
                            fromAmount,
                            fromCurrency,
                            wallet);
                    transaction.id = id;

                    LOG.info("Read {} from the database.", transaction);
                    return transaction;
                }

                // We didn't find a transaction with the given ID. Something's
                // up.
                throw new NoSuchElementException(
                        "Failed to find a transaction with id " + id);
            }
        }
    }

    @SuppressWarnings("unused")
    @Override
    public List<Transaction> loadAll(final Database database)
            throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            @NonNls
            final String sql = "SELECT id, date, to_amount, to_currency, " +
                    "from_amount, from_currency FROM 'transaction';";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                final List<Transaction> transactions = new ArrayList<>();

                while (resultSet.next())
                {
                    @NonNls
                    final String date = resultSet.getString("date");

                    @NonNls
                    final double toAmount = resultSet.getDouble("to_amount");

                    @NonNls
                    final int toCurrency = resultSet.getInt("to_currency");

                    @NonNls
                    final double fromAmount =
                            resultSet.getDouble("from_amount");

                    @NonNls
                    final int fromCurrency = resultSet.getInt("from_currency");

                    @NonNls
                    final int wallet = resultSet.getInt("wallet");

                    final Transaction transaction = new Transaction(
                            Database.toLocalDate(date),
                            toAmount,
                            toCurrency,
                            fromAmount,
                            fromCurrency,
                            wallet);
                    transaction.id = id;

                    transactions.add(transaction);
                    LOG.debug("Read {} from the database.", wallet);
                }

                LOG.info("Read {} transactions from the database.",
                        transactions.size());

                return transactions;
            }
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void delete(final Database database) throws SQLException
    {
        @NonNls
        final String sql = "DELETE FROM 'transaction' WHERE id = ?;";

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
            final String sql = "DELETE FROM 'transaction';";

            statement.executeUpdate(sql);
            LOG.info("Deleted all transactions from the database.");

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
            final String sql = "SELECT COUNT(id) AS n FROM 'transaction';";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                @NonNls
                final int nRows = resultSet.getInt("n");

                LOG.debug("Found {} rows.", nRows);
                return nRows;
            }
        }
    }
}
