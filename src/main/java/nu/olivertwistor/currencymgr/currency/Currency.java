package nu.olivertwistor.currencymgr.currency;

import nu.olivertwistor.currencymgr.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

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

    public void setName(final String name)
    {
        this.name = name;
    }

    public boolean save(final Database database) throws SQLException
    {
        final Connection connection = database.getConnection();

        // First, we have to determine whether this object already exist in the
        // database.
        final String existsInDbSql = "SELECT id FROM currency WHERE id = ?";

        boolean existsInDb;
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
            // The object already exist. We have to do an UPDATE.
            final String updateSql =
                    "UPDATE currency SET ticker = ?, name = ? WHERE id = ?";

            try (final PreparedStatement statement =
                         connection.prepareStatement(updateSql))
            {
                statement.setString(1, this.ticker);
                statement.setString(2, this.name);
                statement.setInt(3, this.id);

                final int nRows = statement.executeUpdate();
                if (nRows > 0)
                {
                    LOG.info("Updated {} in the database.", this);
                    return true;
                }

                LOG.error("Failed to update {} in the database.", this);
            }

            return false;
        }

        // The object doesn't exist. We will do an INSERT.
        final String insertSql =
                "INSERT INTO currency (ticker, name) VALUES (?, ?)";

        try (final PreparedStatement insertStatement =
                     connection.prepareStatement(insertSql))
        {
            insertStatement.setString(1, this.ticker);
            insertStatement.setString(2, this.name);

            final int nRows = insertStatement.executeUpdate();
            if (nRows > 0)
            {
                LOG.info("Inserted {} to the database.", this);
            }
            else
            {
                LOG.error("Failed to insert {} to the database.", this);
            }

            // Now when we have made an INSERT, we can retrieve the last
            // inserted row ID.
            try (final Statement selectStatement = connection.createStatement())
            {
                final String selectSql = "SELECT last_insert_rowid() AS id";

                try (final ResultSet resultSet =
                             selectStatement.executeQuery(selectSql))
                {
                    if (resultSet.next())
                    {
                        this.id = resultSet.getInt("id");
                        LOG.debug("{} received a new ID.", this);

                        return true;
                    }

                    LOG.error("Failed to receive a new ID -- \"SELECT " +
                            "last_insert_rowid() AS id\".");
                }
            }
        }

        return false;
    }

    public static Currency load(final Database database, final int id)
            throws SQLException
    {
        final String selectSql =
                "SELECT ticker, name FROM currency WHERE id = ?";

        try (final PreparedStatement statement =
                     database.getConnection().prepareStatement(selectSql))
        {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    final String ticker = resultSet.getString("ticker");
                    final String name = resultSet.getString("name");

                    final Currency currency = new Currency(ticker, name);
                    currency.id = id;

                    LOG.info("Loaded {} from the database.", currency);
                    return currency;
                }

                throw new NoSuchElementException(
                        "No database record with ID " + id + " was found.");
            }
        }
    }

    @Override
    public String toString()
    {
        return "Currency{" +
                "id=" + this.id +
                ", ticker='" + this.ticker + '\'' +
                ", name='" + this.name + '\'' +
                '}';
    }
}
