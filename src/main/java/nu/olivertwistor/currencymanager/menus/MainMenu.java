package nu.olivertwistor.currencymanager.menus;

import nu.olivertwistor.java.tui.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main menu for this application. This is the top-most menu that is to be
 * presented to the user at the start of the application.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"HardCodedStringLiteral", "StringConcatenation"})
public final class MainMenu implements Menu
{
    private static final Logger LOG = LogManager.getLogger(MainMenu.class);

    private final Map<Integer, String> menuItems;

    /**
     * Creates the menu by calling {@link #define()}, which defines the menu
     * items.
     *
     * @since 0.1.0
     */
    public MainMenu()
    {
        this.menuItems = this.define();
    }

    @Override
    public Map<Integer, String> define()
    {
        final Map<Integer, String> items = new HashMap<>();
        items.put(0, "Quit the application");

        return items;
    }

    @Override
    public boolean act(final int menuItem)
    {
        switch (menuItem)
        {
            case 0:
                return true;
            default:
                return false;
        }
    }

    @Override
    public int show(final boolean allowInvalid) throws IOException
    {
        System.out.println("Main menu");
        System.out.println("=========");
        for (final Map.Entry<Integer, String> item : this.menuItems.entrySet())
        {
            final int option = item.getKey();
            final String description = item.getValue();
            System.out.println(option + ". " + description);
        }

        while (true)
        {
            try
            {
                final int userChoice = Terminal.readInt("? ");

                if (allowInvalid || this.menuItems.containsKey(userChoice))
                {
                    return userChoice;
                }

                System.out.println("That menu item doesn't exist. " +
                        "Please try again.");
            }
            catch (final NumberFormatException e)
            {
                System.out.println("Please enter only numbers.");
            }
        }
    }

    @Override
    public String toString()
    {
        return "MainMenu{" +
                "menuItems=" + this.menuItems +
                '}';
    }
}
