package nu.olivertwistor.currencymgr.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;

public final class MainMenuBar extends JMenuBar
{
    public MainMenuBar(final GUI gui)
    {
        final NewFileAction newFileAction = new NewFileAction(gui);
        final JMenuItem newFile = new JMenuItem(newFileAction);

        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(newFile);

        this.add(fileMenu);
    }
}
