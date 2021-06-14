package nu.olivertwistor.currencymgr.ui;

import nu.olivertwistor.currencymgr.ui.actions.NewFileAction;
import nu.olivertwistor.currencymgr.ui.actions.OpenFileAction;
import nu.olivertwistor.currencymgr.ui.actions.SaveAsFileAction;
import nu.olivertwistor.currencymgr.ui.actions.SaveFileAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;

/**
 * The main menu bar is a JMenuBar with custom menus and menu items attached.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
final class MainMenuBar extends JMenuBar
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Creates a new main menu bar and all its content.
     *
     * @param gui the gui to which this menu bar is attached
     *
     * @since 0.1.0
     */
    MainMenuBar(final GUI gui)
    {
        final NewFileAction newFileAction = new NewFileAction(gui);
        final JMenuItem newFile = new JMenuItem(newFileAction);

        final OpenFileAction openFileAction = new OpenFileAction(gui);
        final JMenuItem openFile = new JMenuItem(openFileAction);

        final SaveFileAction saveFileAction = new SaveFileAction(gui);
        final JMenuItem saveFile = new JMenuItem(saveFileAction);
        saveFile.setEnabled(false);

        final SaveAsFileAction saveAsFileAction = new SaveAsFileAction(gui);
        final JMenuItem saveAsFile = new JMenuItem(saveAsFileAction);
        saveAsFile.setEnabled(false);

        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);

        this.add(fileMenu);

        LOG.trace("Created and populated a JMenuBar.");
    }
}
