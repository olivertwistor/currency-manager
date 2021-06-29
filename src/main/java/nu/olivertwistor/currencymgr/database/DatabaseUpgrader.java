package nu.olivertwistor.currencymgr.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUpgrader
{
    public static void upgrade(final Database database,
                               final int targetVersion) throws SQLException
    {
        final int currentVersion = readCurrentVersion(database);
    }

    public static int readCurrentVersion(final Database database)
            throws SQLException
    {
        int currentVersion = 0;

        final String versionSQL = "SELECT version FROM db_version ORDER BY version DESC LIMIT 1";
        try (final Statement statement = database.getConnection().createStatement())
        {
            try (final ResultSet resultSet = statement.executeQuery(versionSQL))
            {
                if (resultSet.next())
                {
                    currentVersion = resultSet.getInt("version");
                }
            }
        }

        return currentVersion;
    }
}
