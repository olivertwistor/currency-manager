package nu.olivertwistor.currencymgr.database;

import nu.olivertwistor.currencymgr.currency.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * A currency file is the data file in which all data about currencies,
 * accounts, transactions etc are stored. This class holds a {@link Database}
 * (which holds the actual data) as well as various metadata not stored in the
 * database.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public class CurrencyFile
{
    private static final Logger LOG = LogManager.getLogger();

    private final Database database;
    private Currency baseCurrency;

    /**
     * Creates a new currency file object by creating a new {@link Database}
     * object.
     *
     * @param filename where to store the new currency file (path and filename)
     *
     * @since 0.1.0
     */
    public CurrencyFile(final String filename)
    {
        this.database = new Database(filename);
    }

    /**
     * Saves this currency file to the location specified in the constructor.
     *
     * @throws SQLException if a database connection failed to be established
     *
     * @return Whether the save succeeded.
     *
     * @since 0.2.0 Has a boolean return value now, to signal whether the save
     *              succeeded.
     * @since 0.1.0
     */
    public boolean save() throws SQLException
    {
        this.database.getConnection();
        LOG.error("not yet fully implemented");
        return false;
    }

    /**
     * Loads a currency file from the location specified in the constructor.
     *
     * @throws SQLException if a database connection failed to be established
     *
     * @since 0.1.0
     */
    public void load() throws SQLException
    {
        this.database.getConnection();
        LOG.error("not yet fully implemented");
    }

    public void setBaseCurrency(final Currency baseCurrency)
    {
        this.baseCurrency = baseCurrency;
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "CurrencyFile{" +
                "database=" + this.database +
                '}';
    }
}
