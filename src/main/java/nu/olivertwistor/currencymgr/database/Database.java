package nu.olivertwistor.currencymgr.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The database is where the data storage and retrieval happens, to and from
 * the various model classes used in this application.
 *
 * The current implementation has an underlying SQLite data source.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
class Database
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
    Database(final String filename)
    {
        final SQLiteConfig config = new SQLiteConfig();
        config.setEncoding(SQLiteConfig.Encoding.UTF8);

        this.dataSource = new SQLiteDataSource(config);
        final String url = String.format("jdbc:sqlite:%s", filename);
        this.dataSource.setUrl(url);

        LOG.info("Created data source. URL: {}", this.dataSource.getUrl());
    }

    public Connection getConnection() throws SQLException
    {
        final Connection connection = this.dataSource.getConnection();
        LOG.debug("Retrieved a connection from the data source.");

        return connection;
    }
}
