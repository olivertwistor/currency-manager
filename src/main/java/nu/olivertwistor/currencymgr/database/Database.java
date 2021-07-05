package nu.olivertwistor.currencymgr.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The database is where the data storage and retrieval happens, to and from
 * the various model classes used in this application.
 *
 * The current implementation has an underlying SQLite data source.
 *
 * @since 0.1.0
 */
public class Database
{
    private static final Logger LOG = LogManager.getLogger();

    private final SQLiteDataSource dataSource;
    private Connection connection;

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

        LOG.info("Created data source: {}", this.dataSource.getUrl());
    }

    /**
     * Gets the current version of the database.
     *
     * Whenever the database is upgraded through
     * {@link DatabaseUpgrader#upgrade(Database, int)}, a new version number is
     * stored in the database. It's that number that is retrieved by this
     * method.
     *
     * @return The version number; or 0 if either the database is new, or the
     *         table where the version history is stored doesn't exist.
     *
     * @throws SQLException if something went wrong while communicating with
     *                      the database.
     *
     * @since //todo correct version
     */
    public int readCurrentVersion() throws SQLException
    {
        final Connection connection = this.getConnection();

        // First, check if there even is a db_version table. If not, we have a
        // new database file.
        try (final Statement statement = connection.createStatement())
        {
            try (final ResultSet resultSet = statement.executeQuery(
                    "SELECT tbl_name FROM sqlite_master WHERE tbl_name = " +
                            "'db_version'"))
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
            try (final ResultSet resultSet = statement.executeQuery(
                    "SELECT version FROM db_version ORDER BY version DESC " +
                            "LIMIT 1"))
            {
                if (resultSet.next())
                {
                    return resultSet.getInt("version");
                }
            }
        }

        return 0;
    }

    /**
     * Gets a connection to the database. If any of the following is true, a
     * new connection will be established before this method returns.
     * Otherwise, the existing connection will be returned.
     *
     * <ul>
     *     <li>the createNew parameter is true</li>
     *     <li>there is no existing connection</li>
     *     <li>the connection is invalid</li>
     * </ul>
     *
     * @param createNew whether a new connection should be established,
     *                  regardless of whether there already is one
     *
     * @return A connection to the database.
     *
     * @throws SQLException if something went wrong while communicating with
     *                      the database.
     *
     * @since //todo correct version
     */
    @SuppressWarnings("WeakerAccess")
    public Connection getConnection(final boolean createNew) throws SQLException
    {
        if (createNew)
        {
            this.connection = this.dataSource.getConnection();
        }
        else if ((this.connection == null) || !this.connection.isValid(0))
        {
            this.connection = this.dataSource.getConnection();
        }

        return this.connection;
    }

    /**
     * Gets an already established connection to the database. If there is none
     * or if it's invalid, a new connection will be created before this method
     * returns.
     *
     * @return A connection to the database.
     *
     * @throws SQLException if something went wrong while communicating with
     *                      the database.
     *
     * @since //todo correct version
     */
    public Connection getConnection() throws SQLException
    {
        return this.getConnection(false);
    }

    /**
     * Returns a {@link LocalDate} object as a string, formatted as yyyy-MM-dd,
     * for example 2021-07-05.
     *
     * @param date the LocalDate to return as a string
     *
     * @return The provided LocalDate as a ISO date formatted string.
     *
     * @since //todo correct version
     */
    private static String getDate(final LocalDate date)
    {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        final String dateString = date.format(formatter);

        LOG.debug("Formatted {} using {}, resulting in {}.",
                date, formatter, dateString);

        return dateString;
    }

    static String getToday()
    {
        return getDate(LocalDate.now());
    }

    @Override
    public String toString()
    {
        return "Database{" +
                "dataSource=" + this.dataSource +
                '}';
    }
}
