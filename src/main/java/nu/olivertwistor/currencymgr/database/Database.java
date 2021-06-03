package nu.olivertwistor.currencymgr.database;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database
{
    private final SQLiteDataSource dataSource;

    public Database(final String filename)
    {
        final SQLiteConfig config = new SQLiteConfig();
        config.setEncoding(SQLiteConfig.Encoding.UTF8);

        dataSource = new SQLiteDataSource(config);
        final String url = String.format("jdbc:sqlite:%s", filename);
        dataSource.setUrl(url);
    }

    public Connection getConnection() throws SQLException
    {
        return this.dataSource.getConnection();
    }
}
