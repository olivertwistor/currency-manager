package nu.olivertwistor.currencymanager.ui;

import nu.olivertwistor.currencymanager.ui.database.NewDatabaseAction;
import nu.olivertwistor.currencymanager.ui.portfolio.NewPortfolioDialog;
import nu.olivertwistor.currencymanager.ui.portfolio.OpenPortfolioAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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

    private static final long serialVersionUID = -1912239627491741740L;

    /**
     * Creates all menus and menu items in this menu bar.
     *
     * @since 0.1.0
     */
    MainMenuBar(final Frame owner)
    {
        final NewDatabaseAction newDatabaseAction =
                new NewDatabaseAction(owner);

        final JMenuItem newDatabaseMenuItem = new JMenuItem(newDatabaseAction);

        final JMenu databaseMenu = new JMenu("Database");
        databaseMenu.add(newDatabaseMenuItem);

        this.add(databaseMenu);

        LOG.debug("Created the main menu bar.");
    }
}
