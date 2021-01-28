package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class provides access to a {@link SQLiteDataSource} loaded with a
 * certain database file. It also provides convenience methods for various
 * methods further down the stack from SQLiteDatabase.
 *
 * @since  0.1.0
 */
class Database
{
    @SuppressWarnings("unused")
    @NonNls
    private static final Logger LOG = LogManager.getLogger(Database.class);

    @SuppressWarnings("unused")
    @NonNls
    private static final String JDBC_SQLITE_PREFIX = "jdbc:sqlite:";

    private final Connection connection;

    /**
     * Creates a data source from the supplied filename, and connects to it.
     * The connection can later be retrieved by calling
     * {@link #getConnection()}.
     *
     * @param filename path to the SQLite database (note that the prefix
     *                 shouldn't be included in this string)
     *
     * @throws SQLException if the creation of the data source or the
     *                      connection to it failed.
     *
     * @since 0.1.0
     */
    Database(final String filename) throws SQLException
    {
        final SQLiteDataSource dataSource = new SQLiteDataSource();
        final String url = JDBC_SQLITE_PREFIX + filename;
        dataSource.setUrl(url);
        this.connection = dataSource.getConnection();
    }

    Connection getConnection()
    {
        return this.connection;
    }

    /**
     * Reads the current version number of this database as stored in the
     * database file. If the database in newly created, no version table will
     * exist and this method returns 0.
     *
     * @return The current database version, or 0 if version table or row(s)
     *         couldn't be found.
     *
     * @throws SQLException if there were any problems with the connection to
     *                      this database or with execiting queries.
     *
     * @since 0.1.0
     */
    public int readCurrentDbVersion() throws SQLException
    {
        // First, we must determine whether the version table exists at
        // all. If it doesn't, we're dealing with a brand new database,
        // and that means the current DB version is 0.
        try (final Statement statement =
                     this.getConnection().createStatement())
        {
            final String sql = "SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='db_version'";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                if (!resultSet.next())
                {
                    return 0;
                }
            }
        }

        // There is a version table, so now we can proceed to look at the
        // current version. The current version is the row with the latest date.
        try (final Statement statement =
                     this.getConnection().createStatement())
        {
            final String sql = "SELECT version FROM db_version ORDER BY " +
                    "date, version DESC LIMIT 1";

            try (final ResultSet resultSet = statement.executeQuery(sql))
            {
                if (resultSet.next())
                {
                    final int version = resultSet.getInt("version");
                    return version;
                }

                return 0;
            }
        }
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Database{" +
                "connection=" + this.connection +
                '}';
    }
}
