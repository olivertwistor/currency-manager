package nu.olivertwistor.currencymanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

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

    private static final String jdbc_db_url_prefix = "jdbc:sqlite:";

    private final SQLiteDataSource dataSource;

    /**
     * Creates a new SQLiteDataSource and points it to the provided database
     * file.
     *
     * @param path file path to the database, relative to the classpath
     *             (remember the forward slash in the beginning)
     *
     * @throws FileNotFoundException if the specified database couldn't be
     *                               found
     * @throws URISyntaxException    if the path to the database is invalid
     *
     * @since 0.1.0
     */
    public Database(final String path)
            throws FileNotFoundException, URISyntaxException
    {
        // First, check whether the database file exist. If not, throw an
        // exception.
        final URL resource = this.getClass().getResource(path);
        if (resource == null)
        {
            throw new FileNotFoundException(path + " does not exist.");
        }
        final File file = Paths.get(resource.toURI()).toFile();
        final String fileAbsPath = file.getAbsolutePath();
        if (!file.isFile())
        {
            throw new FileNotFoundException(fileAbsPath + " isn't a file.");
        }

        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl(jdbc_db_url_prefix + fileAbsPath);

        log.info("Loaded database {0}", fileAbsPath);
    }

    public SQLiteDataSource getDataSource()
    {
        return this.dataSource;
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

    @Override
    public String toString()
    {
        return "Database{dataSource=" + this.dataSource + "}";
    }
}
