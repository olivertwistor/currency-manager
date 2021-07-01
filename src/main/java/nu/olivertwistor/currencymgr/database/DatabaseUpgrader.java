package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUpgrader
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger();

    public static void upgrade(final Database database,
                               final int targetVersion) throws SQLException
    {
        final int currentVersion = database.readCurrentVersion();

        LOG.info("DB versions current/target: {}/{}", currentVersion,
                targetVersion);

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
            try (final Statement statement =
                         database.getConnection().createStatement())
            {
                final String createTableDbVersion =
                        FileUtils.loadResourceToString(
                                "/db/0/create-table-dbversion.sql",
                                DatabaseUpgrader.class);
                //noinspection JDBCExecuteWithNonConstantString
                statement.executeUpdate(createTableDbVersion);
            }

            LOG.info("Updated the DB from version 0 to 1");
        }
    }
}
