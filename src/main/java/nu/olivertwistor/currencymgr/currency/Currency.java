package nu.olivertwistor.currencymgr.currency;

import nu.olivertwistor.currencymgr.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        @NonNls
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
            @NonNls
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
                    LOG.info("Updated the record in the database.");
                    return true;
                }

                LOG.error("Failed to update the record in the database.");
            }

            return false;
        }

        // The object doesn't exist. We will do an INSERT.
        @NonNls
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
                LOG.info("Inserted a new record to the database.");
            }
            else
            {
                LOG.error("Failed to insert a new record to the database.");
            }

            // Now when we have made an INSERT, we can retrieve the last
            // inserted row ID.
            try (final Statement selectStatement = connection.createStatement())
            {
                @NonNls
                final String selectSql = "SELECT last_insert_rowid() AS id";

                try (final ResultSet resultSet =
                             selectStatement.executeQuery(selectSql))
                {
                    if (resultSet.next())
                    {
                        this.id = resultSet.getInt("id");
                        LOG.debug("The new record got ID #{}.", this.id);

                        return true;
                    }
                }
            }
        }

        return false;
    }
}
