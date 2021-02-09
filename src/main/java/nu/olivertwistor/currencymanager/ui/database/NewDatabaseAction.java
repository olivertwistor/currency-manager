package nu.olivertwistor.currencymanager.ui.database;

import nu.olivertwistor.currencymanager.db.Database;
import nu.olivertwistor.currencymanager.db.DatabaseUpgrader;
import nu.olivertwistor.currencymanager.ui.MainWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

/**
 * The action "the user wants to create a new database file".
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardCodedStringLiteral")
public final class NewDatabaseAction extends AbstractAction
{
    @NonNls
    private static final Logger LOG =
            LogManager.getLogger(NewDatabaseAction.class);

    private static final long serialVersionUID = 1L;

    private final MainWindow parent;

    /**
     * Creates a new {@link Action} for the user creating a new database. This
     * adds a name, description and a mnemonic key.
     *
     * @param parent MainWindow instance
     *
     * @since 0.1.0
     */
    public NewDatabaseAction(final MainWindow parent)
    {
        super("New database");
        this.parent = parent;

        this.putValue(SHORT_DESCRIPTION, "Create a new database file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        LOG.debug("New database action performed.");

        final JFileChooser fileChooser = new JFileChooser(".");
        final int choice = fileChooser.showSaveDialog(this.parent);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            final File file = fileChooser.getSelectedFile();
            try
            {
                final Database database =
                        new Database(file.getAbsolutePath());
                DatabaseUpgrader.upgrade(database);
                this.parent.setDatabase(database);
            }
            catch (final SQLException exception)
            {
                LOG.error("Failed to create or update the database.",
                        exception);

                JOptionPane.showMessageDialog(
                        this.parent,
                        "Failed to create a new database.",
                        "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "NewDatabaseAction{" +
                "parent=" + this.parent +
                '}';
    }
}
