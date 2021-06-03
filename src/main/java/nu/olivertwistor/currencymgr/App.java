package nu.olivertwistor.currencymgr;

import nu.olivertwistor.currencymgr.ui.GUI;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

public final class App
{
    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
        SwingUtilities.invokeAndWait(() ->
        {
            final GUI gui = new GUI("Currency Manager");
            gui.attachMainMenuBar();
            gui.setVisible(true);
        });
    }
}
