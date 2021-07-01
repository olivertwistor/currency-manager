package nu.olivertwistor.currencymgr.currency;

import nu.olivertwistor.currencymgr.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Currency
{
    private static final Logger LOG = LogManager.getLogger();

    private int id;
    private final String ticker;
    private String name;

    public Currency(final String ticker, final String name)
    {
        this.id = 0;
        this.ticker = ticker;
        this.name = name;
    }

    public Currency(final String ticker)
    {
        this(ticker, "");
    }

    public boolean save(final Database database) throws SQLException
    {
        final Connection connection = database.getConnection();

        // First, we have to determine whether this object already exist in the
        // database.
        @NonNls
        final String existsInDbSql = "SELECT id FROM currency WHERE id = ?";

        boolean existsInDb = false;
        try (final PreparedStatement statement =
                     connection.prepareStatement(existsInDbSql))
        {
            statement.setInt(1, this.id);
            try (final ResultSet resultSet = statement.executeQuery())
            {
                existsInDb = resultSet.next();
            }
        }

        if (existsInDb)
        {

        }
    }
}
