package nu.olivertwistor.currencymanager.db;

import nu.olivertwistor.currencymanager.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Upgrader
{
    private static final Logger log = LogManager.getLogger(Upgrader.class);

    private final Database database;
    private int currentDbVersion;
    private final int targetDbVersion;

    public Upgrader(final Database database, final Settings settings)
            throws SQLException
    {
        this.database = database;
        this.currentDbVersion = this.database.readCurrentDbVersion();
        this.targetDbVersion = settings.getDatabaseVersion();
    }

    public void upgrade() throws DatabaseUpgradeException
    {
        log.info("Attempting to upgrade the database from version {0} to " +
                "version {1}.", this.currentDbVersion, this.targetDbVersion);
    }
}
