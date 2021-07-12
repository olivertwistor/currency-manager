package nu.olivertwistor.currencymgr.currency;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.List;

public class CurrencyDialog extends JDialog
{
    private List<Currency> currencies;

    public CurrencyDialog(final JFrame owner)
    {
        super(owner, "Choose currency", true);
    }
}
