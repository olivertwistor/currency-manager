package nu.olivertwistor.currencymgr.ui;

import nu.olivertwistor.currencymgr.utils.FileUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Arrays;

public final class CurrencyFileFilter extends FileFilter
{
    private static final String[] ACCEPTED_EXTENSIONS = { "ocm" };

    @Override
    public boolean accept(final File pathname)
    {
        // Accept directories so the user can traverse the directory tree.
        if (pathname.isDirectory())
        {
            return true;
        }

        // Only accept *.ocm (Olivertwistor Currency Manager) files.
        final String extension = FileUtils.getExtension(pathname);
        return Arrays.asList(ACCEPTED_EXTENSIONS).contains(extension);
    }

    @Override
    public String getDescription()
    {
        return "Currency Manager (*.ocm)";
    }
}
