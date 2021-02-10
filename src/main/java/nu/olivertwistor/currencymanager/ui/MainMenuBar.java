package nu.olivertwistor.currencymanager.ui;

import nu.olivertwistor.currencymanager.ui.database.NewDatabaseAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The menu bar that is attached to the main window.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
final class MainMenuBar extends JMenuBar
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(MainMenuBar.class);

    private static final long serialVersionUID = 1L;

    /**
     * Creates all actions, menus and menu items in this menu bar.
     *
     * @param owner MainWindow instance to which this menu bar is attached
     *
     * @since 0.1.0
     */
    MainMenuBar(final MainWindow owner)
    {
        // Actions for the File menu.
        final NewDatabaseAction newDatabaseAction =
                new NewDatabaseAction(owner);
        final ExitAppAction exitAppAction = new ExitAppAction();

        // Menu items in the File menu.
        final JMenuItem newDatabaseMenuItem =
                new JMenuItem(newDatabaseAction);
        final JMenuItem openDatabaseMenuItem =
                new JMenuItem("Open a database...");
        openDatabaseMenuItem.setEnabled(false);

        // Construct the File menu.
        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(newDatabaseMenuItem);
        fileMenu.add(openDatabaseMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitAppAction);

        // Menu items in the Manage menu.
        final JMenuItem managePortfoliosMenuItem =
                new JMenuItem("Manage portfolios...");
        managePortfoliosMenuItem.setEnabled(false);
        final JMenuItem manageWalletsMenuItem =
                new JMenuItem("Manage wallets...");
        manageWalletsMenuItem.setEnabled(false);
        final JMenuItem manageCurrenciesMenuItem =
                new JMenuItem("Manage currencies...");
        manageCurrenciesMenuItem.setEnabled(false);

        // Construct the Manage menu.
        final JMenu manageMenu = new JMenu("Manage");
        manageMenu.add(managePortfoliosMenuItem);
        manageMenu.add(manageWalletsMenuItem);
        manageMenu.add(manageCurrenciesMenuItem);

        // Add all menus to this menu bar.
        this.add(fileMenu);
        this.add(manageMenu);

        LOG.debug("Created the main menu bar.");
    }
}
