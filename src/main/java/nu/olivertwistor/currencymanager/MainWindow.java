package nu.olivertwistor.currencymanager;

import javax.swing.JFrame;
import java.awt.Dimension;

public final class MainWindow extends JFrame
{
    public MainWindow(final String title, final Dimension size)
    {
        super(title);
        this.setSize(size);

        // When this JFrame closes, the app should exit.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
