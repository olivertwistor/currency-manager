package nu.olivertwistor.currencymanager;

import nu.olivertwistor.currencymanager.menus.MainMenu;
import nu.olivertwistor.currencymanager.menus.Menu;

import java.io.IOException;

/**
 * Main class for the Currency Manager application. Handles starting the app.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"UtilityClassCanBeEnum", "ClassWithoutLogger", "ClassOnlyUsedInOnePackage", "ClassUnconnectedToPackage"})
final class App
{
    /**
     * Starts the main menu and loops it until the user chooses to exit the
     * application.
     *
     * @param args not used
     *
     * @throws IOException if user input failed to be read
     *
     * @since 0.1.0
     */
    @SuppressWarnings({"MethodCanBeVariableArityMethod", "PublicMethodWithoutLogging"})
    public static void main(final String[] args) throws IOException
    {
        final Menu mainMenu = new MainMenu();

        boolean exit = false;
        while (!exit)
        {
            final int choice = mainMenu.show(false);
            exit = mainMenu.act(choice);
        }
    }
}
