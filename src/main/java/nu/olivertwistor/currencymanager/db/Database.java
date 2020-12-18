package nu.olivertwistor.currencymanager.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides access to a {@link SQLiteDataSource} loaded with a
 * certain database file. It also provides convenience methods for various
 * methods further down the stack from SQLiteDatabase.
 *
 * @since  0.1.0
 */
public class Database
{
    private static final Logger log = LogManager.getLogger(Database.class);

    private static final String jdbc_db_url_prefix = "jdbc:sqlite:/";

    private final SQLiteDataSource dataSource;

    /**
     * Creates a new SQLiteDataSource and points it to the provided database
     * file.
     *
     * @param path file path to the database, relative to the location of the
     *             app
     *
     * @since 0.1.0
     */
    public Database(final String path) throws FileNotFoundException
    {
        final String fileAbsPath = new File(path).getAbsolutePath();

        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl(jdbc_db_url_prefix + fileAbsPath);

        log.info("Loaded database {0}", fileAbsPath);
    }

    /**
     * Establishes a connection to the database.
     *
     * @return The connection object.
     *
     * @throws SQLException if the connection failed
     *
     * @since 0.1.0
     */
    public Connection getConnection() throws SQLException
    {
        return this.dataSource.getConnection();
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

    @Override
    public String toString()
    {
        return "Database{dataSource=" + this.dataSource + "}";
    }
}
