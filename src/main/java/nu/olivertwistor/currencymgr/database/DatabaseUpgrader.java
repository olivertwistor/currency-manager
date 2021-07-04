package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUpgrader
{
    private static final Logger LOG = LogManager.getLogger();

    @SuppressWarnings("StaticMethodOnlyUsedInOneClass")
    public static void upgrade(final Database database,
                               final int targetVersion) throws SQLException
    {
        final int currentVersion = database.readCurrentVersion();

        LOG.info("The current database version is {}, and the target version " +
                "is {}.", currentVersion, targetVersion);

        if (currentVersion >= targetVersion)
        {
            LOG.info("Since current version is the same as or later than " +
                    "target version, no upgrade is necessary.");
            return;
        }

        // If current version is 0, we know that there is no database set up,
        // so we need to create it.
        if (currentVersion <= 0)
        {
            executeUpdateFromResStream(database,
                    "/db/0/create-table-dbversion.sql"); //NON-NLS
            executeUpdateFromResStream(database,
                    "/db/0/create-index-dbversion-version.sql"); //NON-NLS
            executeUpdateFromResStream(database,
                    "/db/0/create-table-currency.sql"); //NON-NLS

            database.writeCurrentVersion(1);
            LOG.info("Updated the database from version 0 to 1.");
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
