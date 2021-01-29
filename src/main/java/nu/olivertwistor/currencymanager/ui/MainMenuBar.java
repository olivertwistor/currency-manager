package nu.olivertwistor.currencymanager.ui;

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
        final JMenuItem newPortfolioItem =
                new JMenuItem("New portfolio", KeyEvent.VK_N);
        newPortfolioItem.addActionListener((ActionEvent event) -> {
            new NewPortfolioDialog(owner);
        });
        final Action openPortfolioAction = new OpenPortfolioAction();
        final Action exitAppAction = new ExitAppAction();

        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(newPortfolioItem);
        fileMenu.add(openPortfolioAction);
        fileMenu.add(exitAppAction);

        final JMenu editMenu = new JMenu("Edit");
        final JMenu helpMenu = new JMenu("Help");

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);

        LOG.debug("Created the main menu bar.");
    }
}
