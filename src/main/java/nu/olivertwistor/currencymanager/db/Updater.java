package nu.olivertwistor.currencymanager.db;

import java.sql.SQLException;

public class Updater
{
    private int dbVersion;

    public Updater(final Database database) throws SQLException
    {
        this.dbVersion = database.readCurrentDbVersion();
    }
}
