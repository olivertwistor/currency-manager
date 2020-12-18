package nu.olivertwistor.currencymanager;

import nu.olivertwistor.currencymanager.db.Database;

import java.io.FileNotFoundException;

public final class App
{
    private App()
    {
        // Look for the database file. If it doesn't exist, create it and
        // execute the first database migration script.
        final Database database;
        try
        {
            database = new Database("currency-manager.sql");
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // Update the database to the current version as defined in
        // app.properties.


        throw new UnsupportedOperationException("not implemented");
    }

    public static void main(final String[] args)
    {
        // We want one argument: path to the database file to use.
        if (args.length != 1)
        {
            printUsage();
        }

        new App();
    }

    /**
     * Prints the correct way to start the app, to stdout.
     *
     * @since 0.1.0
     */
    private static void printUsage()
    {
        System.out.println("Usage:   java -jar [app filename] [path to " +
                "database file]");
        System.out.println("Example: java -jar currency-manager-1.0.3.jar " +
                "db.sqlite3");
        System.out.println();
        System.out.println("The path to database file is relative to the " +
                "location of the app. Note that this file doesn't have to " +
                "exist. If it doesn't, it will be created.");
    }
}
