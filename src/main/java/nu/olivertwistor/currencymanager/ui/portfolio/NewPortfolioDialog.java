package nu.olivertwistor.currencymanager.ui.portfolio;

import nu.olivertwistor.currencymanager.db.Portfolio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;

/**
 * This dialog lets the user input the necessary information to create a new
 * {@link Portfolio portfolio}.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"HardCodedStringLiteral", "unused"})
public final class NewPortfolioDialog extends JDialog
{
    private static final Logger LOG =
            LogManager.getLogger(NewPortfolioDialog.class);

    private static final long serialVersionUID = 4424307366179256376L;

    private final JTextField nameField;
    private final JTextField baseCurrencyField;

    /**
     * Creates the dialog and makes it visible.
     *
     * @param owner the GUI window to which this dialog belongs
     *
     * @since 0.1.0
     */
    public NewPortfolioDialog(final Window owner)
    {
        super(owner, "New portfolio", ModalityType.APPLICATION_MODAL);

        final JLabel nameLabel = new JLabel("Name");
        final JLabel baseCurrencyLabel = new JLabel("Base currency");

        this.nameField = new JTextField();
        nameLabel.setLabelFor(this.nameField);

        this.baseCurrencyField = new JTextField();
        baseCurrencyLabel.setLabelFor(this.baseCurrencyField);

        final Component okButton = new JButton("OK");
        final Component cancelButton = new JButton("Cancel");

        final Container formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(nameLabel);
        formPanel.add(this.nameField);
        formPanel.add(baseCurrencyLabel);
        formPanel.add(this.baseCurrencyField);
        formPanel.add(okButton);
        formPanel.add(cancelButton);

        this.setContentPane(formPanel);

        this.pack();
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

    @SuppressWarnings("unused")
    public Portfolio getUserInput()
    {
        final String name = this.nameField.getText();
        final String baseCurrency = this.baseCurrencyField.getText();

        LOG.debug("Reading user input: name='{}', baseCurrency='{}'.",
                name, baseCurrency);

        return new Portfolio(name, 0);
    }

    @SuppressWarnings("PublicMethodWithoutLogging")
    @Override
    public String toString()
    {
        return "NewPortfolioDialog{" +
                "nameField=" + this.nameField +
                ", baseCurrencyField=" + this.baseCurrencyField +
                '}';
    }
}
