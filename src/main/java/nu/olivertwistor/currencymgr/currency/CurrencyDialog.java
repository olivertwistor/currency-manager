package nu.olivertwistor.currencymgr.currency;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.List;

public class ChooseCurrencyDialog extends JDialog
{
    private List<Currency> currencies;

    public ChooseCurrencyDialog(final JFrame owner)
    {
        super(owner, "Choose currency", true);
    }
}
