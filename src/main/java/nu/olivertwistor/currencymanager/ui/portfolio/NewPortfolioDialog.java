package nu.olivertwistor.currencymanager.ui.portfolio;

import nu.olivertwistor.currencymanager.db.Portfolio;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public final class NewPortfolioDialog extends JDialog
{
    private final JTextField nameField;
    private final JTextField baseCurrencyField;

    public NewPortfolioDialog(final Frame owner)
    {
        super(owner, "New portfolio", ModalityType.APPLICATION_MODAL);

        final JLabel nameLabel = new JLabel("Name");
        final JLabel baseCurrencyLabel = new JLabel("Base currency");

        this.nameField = new JTextField();
        nameLabel.setLabelFor(nameField);

        baseCurrencyField = new JTextField();
        baseCurrencyLabel.setLabelFor(baseCurrencyField);

        final JButton okButton = new JButton("OK");
        final JButton cancelButton = new JButton("Cancel");

        final JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(baseCurrencyLabel);
        formPanel.add(baseCurrencyField);
        formPanel.add(okButton);
        formPanel.add(cancelButton);

        this.setContentPane(formPanel);

        this.pack();
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

    public Portfolio getUserInput()
    {
        final String name = this.nameField.getText();
        final String baseCurrency = this.nameField.getText();

        return new Portfolio(name, 0);
    }
}
