package nu.olivertwistor.currencymgr.currency;

import nu.olivertwistor.currencymgr.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class Currency
{
    private static final Logger LOG = LogManager.getLogger();

    private final String ticker;
    private String name;

    public Currency(final String ticker, final String name)
    {
        this.ticker = ticker;
        this.name = name;
    }

    public Currency(final String ticker)
    {
        this(ticker, "");
    }

    public boolean save(final Database database) throws SQLException
    {
        database.getConnection();
        LOG.error("not implemented");
        return false;
    }
}
