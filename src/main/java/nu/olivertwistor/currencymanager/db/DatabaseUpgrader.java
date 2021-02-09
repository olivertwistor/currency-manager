package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles database upgrades between versions.
 *
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public final class DatabaseUpgrader
{
    @NonNls
    private static final Logger LOG =
            LogManager.getLogger(DatabaseUpgrader.class);

    /**
     * Upgrades a database from its current version to the latest version.
     *
     * @param database the database to upgrade
     *
     * @throws SQLException if the upgrade failed; or, more generally, if
     *                      there were problems communicating with the database.
     *
     * @since 0.1.0
     */
    public static void upgrade(final Database database) throws SQLException
    {
        LOG.info("Database upgrade process started.");

        final int currentDbVersion = database.readCurrentDbVersion();
        LOG.debug("Current database version: {}", currentDbVersion);

        if (currentDbVersion < 1)
        {
            LOG.info("Upgrading the database version to 1.");

            // Start a transaction.
            final Connection connection = database.getConnection();
            connection.setAutoCommit(false);
            LOG.debug("Disabled auto commit.");

            try (final Statement statement =
                         database.getConnection().createStatement())
            {
                @NonNls
                final String createDbVersionTable =
                        "CREATE TABLE IF NOT EXISTS db_version (" +
                                "date TEXT NOT NULL, " +
                                "version INTEGER NOT NULL, " +
                                "PRIMARY KEY (date, version)" +
                        ");";

                statement.executeUpdate(createDbVersionTable);
                LOG.debug("Created table: db_version");

                @NonNls
                final String createCurrencyTable =
                        "CREATE TABLE IF NOT EXISTS currency (" +
                                "id INTEGER PRIMARY KEY, " +
                                "ticker TEXT NOT NULL, " +
                                "description TEXT" +
                        ");";

                statement.executeUpdate(createCurrencyTable);
                LOG.debug("Created table: currency");

                @NonNls
                final String createPortfolioTable =
                        "CREATE TABLE IF NOT EXISTS portfolio (" +
                                "id INTEGER PRIMARY KEY, " +
                                "name TEXT NOT NULL, " +
                                "base_currency INTEGER NOT NULL, " +
                                "FOREIGN KEY (base_currency) " +
                                "REFERENCES currency(id) " +
                                "ON UPDATE CASCADE ON DELETE CASCADE" +
                        ");";

                statement.executeUpdate(createPortfolioTable);

                // If we have come this far, we can commit the transaction.
                connection.commit();
                LOG.debug("Committed the transaction.");

                LOG.info("Completed the upgrade to version 1.");
            }
            catch (final SQLException e)
            {
                connection.rollback();
                LOG.warn("Rollbacked the transaction.", e);
                throw new SQLException(e);
            }
            finally
            {
                connection.setAutoCommit(true);
                LOG.debug("Enabled auto commit.");
            }
        }

        LOG.info("Database upgrade process completed.");
    }

    @SuppressWarnings("JavaDoc")
    private DatabaseUpgrader() { }
}
