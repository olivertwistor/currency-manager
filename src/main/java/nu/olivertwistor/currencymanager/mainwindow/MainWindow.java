package nu.olivertwistor.currencymanager.mainwindow;

import org.jetbrains.annotations.Nls;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Main window for Currency Manager. When the app starts, this window will be
 * shown. When the user closes this window, the app will exit.
 *
 * @since 0.1.0
 */
public final class MainWindow extends JFrame
{
    private static final long serialVersionUID = 7830866209130181043L;

    /**
     * Creates the window, sets its title and initial size. Adds the main menu
     * bar and all the visual components of the window.
     *
     * @param title window title
     * @param size  initial size of this window
     *
     * @since 0.1.0
     */
    public MainWindow(final @Nls String title, final Dimension size)
    {
        super(title);
        this.setSize(size);

        // When this JFrame closes, the app should exit.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a JPanel as the content pane.
        final Container contentPanel = new JPanel(new BorderLayout());
        this.setContentPane(contentPanel);

        final JMenuBar mainMenuBar = new MainMenuBar();
        this.setJMenuBar(mainMenuBar);
    }
}
