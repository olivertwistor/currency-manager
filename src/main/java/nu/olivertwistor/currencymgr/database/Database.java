package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The database is where the data storage and retrieval happens, to and from
 * the various model classes used in this application.
 *
 * The current implementation has an underlying SQLite data source.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public class Database
{
    private static final Logger LOG = LogManager.getLogger();

    private final SQLiteDataSource dataSource;

    /**
     * Creates a new database object from a specified filename. A data source
     * is created, but no connection is established in this method. Call
     * {@link #getConnection()} for that.
     *
     * @param filename path to the database file to use; if it doesn't exist,
     *                 it will be created; the JDBC connection prefix will be
     *                 added automatically, so this parameter should only
     *                 consist of the path
     *
     * @since 0.1.0
     */
    public Database(final String filename)
    {
        final SQLiteConfig config = new SQLiteConfig();
        config.setEncoding(SQLiteConfig.Encoding.UTF8);

        this.dataSource = new SQLiteDataSource(config);
        final String url = String.format("jdbc:sqlite:%s", filename);
        this.dataSource.setUrl(url);

        LOG.info("Created data source. URL: {}", this.dataSource.getUrl());
    }

    public int readCurrentVersion() throws SQLException
    {
        final Connection connection = this.getConnection();

        // First, check if there even is a db_version table. If not, we have a
        // new database file.
        try (final Statement statement = connection.createStatement())
        {
            @NonNls
            final String tableSql = "SELECT tbl_name FROM sqlite_master " +
                    "WHERE tbl_name = 'db_version'";

            try (final ResultSet resultSet = statement.executeQuery(tableSql))
            {
                if (!resultSet.next())
                {
                    return 0;
                }
            }
        }

        // Now we now that there is a db_version table. Let's read the latest
        // version.
        try (final Statement statement = connection.createStatement())
        {
            @NonNls
            final String versionSql = "SELECT version FROM db_version " +
                    "ORDER BY version DESC LIMIT 1";

            try (final ResultSet resultSet = statement.executeQuery(versionSql))
            {
                if (resultSet.next())
                {
                    return resultSet.getInt("version");
                }
            }
        }

        return 0;
    }

    public void writeCurrentVersion(final int version) throws SQLException
    {
        @NonNls
        final String sql = "INSERT INTO db_version (version, date) VALUES " +
                "(?, ?)";

        try (final PreparedStatement statement =
                     this.getConnection().prepareStatement(sql))
        {
            statement.setInt(1, version);
            statement.setString(2, DateUtils.getToday());

            final int nRows = statement.executeUpdate();
        }
    }

    public Connection getConnection() throws SQLException
    {
        final Connection connection = this.dataSource.getConnection();
        LOG.debug("Retrieved a connection from the data source.");

        return connection;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Database{" +
                "dataSource=" + this.dataSource +
                '}';
    }
}
