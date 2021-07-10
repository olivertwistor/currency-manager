package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles the upgrading and downgrading of the database.
 *
 * The method {@link #upgrade(Database, int)} executes the necessary SQL
 * statements to bring the database up to a certain version, for example
 * creating tables and adding indices. This is enabling users to use a database
 * made in a previous version of Currency Manager, and make it compatible with
 * the current version, (hopefully) without losing any data.
 *
 * The method {@link #downgrade(Database, int)} does the same thing as
 * {@code upgrade(Database, int)}, but for converting a newer database to be
 * compatible with a lower version of Currency Manager. Note that this will
 * likely result in loss of data.
 *
 * @since //todo correct version
 */
public final class DatabaseUpgrader
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Upgrades the database from its current version to a target version, by
     * executing a series of SQL statements.
     *
     * If the current version is greater than or equal to target version, this
     * method will do nothing, except simply return true. Otherwise, it will go
     * through step by step the upgrade process, one version at a time. For
     * example, a database at version 2 and target version 4 will first be
     * upgraded from 2 to 3, then from 3 to 4.
     *
     * Please note that database version aren't equivalent to application
     * version. The same application version (e.g. 1.3.0) may see multiple
     * database versions, and vice versa, a single database verion may see
     * multiple application versions, if the application changes don't involve
     * changes in the database.
     *
     * @param database which database to upgrade
     * @param targetVersion the version to which to upgrade (note that this is
     *                      not the same as application version)
     *
     * @return Whether the complete upgrade process succeeded. Returns true if
     *         there's no need to upgrade.
     *
     * @throws SQLException if something went wrong while communicating with
     *                      the database.
     *
     * @since //todo correct version
     */
    @SuppressWarnings("StaticMethodOnlyUsedInOneClass")
    public static boolean upgrade(final Database database,
                                  final int targetVersion) throws SQLException
    {
        final int currentVersion = database.readCurrentVersion();

        LOG.info("The current database version is {}, and the target version " +
                "is {}.", currentVersion, targetVersion);

        if (currentVersion >= targetVersion)
        {
            LOG.info("Since current version is the same as or later than " +
                    "target version, no upgrade is necessary.");
            return true;
        }

        boolean success = false;

        if (currentVersion <= 0)
        {
            // If current version is 0, we know that there is no database set
            // up, so we need to create it.
            executeUpdateFromResStream(database,
                    "/db/0/create-table-dbversion.sql"); //NON-NLS
            executeUpdateFromResStream(database,
                    "/db/0/create-index-dbversion-version.sql"); //NON-NLS
            executeUpdateFromResStream(database,
                    "/db/0/create-table-currency.sql"); //NON-NLS

            // Write back the updated version number.
            success = writeCurrentVersion(database.getConnection(), 1);
            LOG.info("Updated the database from version 0 to 1.");
        }

        return success;
    }

    public static boolean downgrade(final Database database,
                                    final int targetVersion)
            throws SQLException
    {
        LOG.error("not implemented");
        throw new UnsupportedOperationException("not implemented");
    }

    private static boolean writeCurrentVersion(final Connection connection,
                                               final int version)
            throws SQLException
    {
        try (final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO db_version (version, date) VALUES (?, ?)"))
        {
            statement.setInt(1, version);
            statement.setString(2, Database.getToday());

            final int nRows = statement.executeUpdate();
            if (nRows > 0)
            {
                LOG.info("Inserted a new database version: {}", version);
                return true;
            }

            LOG.error("Failed to insert a new database version: {}",
                    version);
            return false;
        }
    }

    @SuppressWarnings("JDBCExecuteWithNonConstantString")
    private static void executeUpdateFromResStream(final Database database,
                                                   final String stream)
            throws SQLException
    {
        try (final Statement statement =
                     database.getConnection().createStatement())
        {
            final String sqlString = FileUtils.loadResourceToString(
                    stream, DatabaseUpgrader.class);
            statement.executeUpdate(sqlString);
        }
    }

    private DatabaseUpgrader()
    {
        // Just for preventing instantiation.
    }
}
