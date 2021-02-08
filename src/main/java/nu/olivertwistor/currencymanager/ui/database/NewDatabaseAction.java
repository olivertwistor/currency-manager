package nu.olivertwistor.currencymanager.ui.database;

import nu.olivertwistor.currencymanager.db.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

public final class NewDatabaseAction extends AbstractAction
{
    private static final Logger LOG =
            LogManager.getLogger(NewDatabaseAction.class);

    private final Component parent;
    private Database database;

    public NewDatabaseAction(final Component parent)
    {
        super("New database");
        this.parent = parent;

        this.putValue(SHORT_DESCRIPTION, "Create a new database file.");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        final JFileChooser fileChooser = new JFileChooser(".");
        final int choice = fileChooser.showSaveDialog(this.parent);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            final File file = fileChooser.getSelectedFile();
            try
            {
                this.database = new Database(file.getAbsolutePath());
            }
            catch (SQLException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public Database getDatabase()
    {
        return this.database;
    }
}
