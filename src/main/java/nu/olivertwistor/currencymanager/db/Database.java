package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class defines a thin wrapper around a SQLite3 database source.
 *
 * @since 0.1.0
 */
public class Database
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
    public Database(final String filename) throws SQLException
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

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "Database{" +
                "connection=" + this.connection +
                '}';
    }
}
