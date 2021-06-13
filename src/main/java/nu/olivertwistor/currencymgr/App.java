package nu.olivertwistor.currencymgr;

import nu.olivertwistor.currencymgr.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

/**
 * Starting class for the Currency Manager app. Responsible for creating and
 * showing the GUI.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"HardCodedStringLiteral", "ClassOnlyUsedInOnePackage"})
final class App
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Creates and shows the GUI on the AWT event thread.
     *
     * @param args unused program arguments
     *
     * @throws InterruptedException      if the AWT thread was interrupted
     * @throws InvocationTargetException if the code that creates and shows the
     *                                   GUI throws an exception
     *
     * @since 0.1.0
     */
    public static void main(final String[] args)
            throws InterruptedException, InvocationTargetException
    {
        SwingUtilities.invokeAndWait(App::createAndShowGUI);
    }

    /**
     * Creates a new {@link GUI} object, attaches a menu bar and shows the GUI.
     *
     * @since 0.1.0
     */
    private static void createAndShowGUI()
    {
        final GUI gui = new GUI("Currency Manager");
        gui.attachMainMenuBar();
        gui.setVisible(true);

        LOG.info("Started the app.");
    }
}
