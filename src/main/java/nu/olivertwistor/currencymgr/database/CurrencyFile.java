package nu.olivertwistor.currencymgr.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class CurrencyFile
{
    private static final Logger LOG = LogManager.getLogger();

    private final Database database;

    public CurrencyFile(final String filename)
    {
        this.database = new Database(filename);
    }

    public void save() throws SQLException
    {
        //database.getConnection();
        LOG.error("not yet implemented");
    }
}
