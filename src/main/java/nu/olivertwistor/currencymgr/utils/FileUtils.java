package nu.olivertwistor.currencymgr.utils;

import java.io.File;
import java.util.Locale;

public final class FileUtils
{
    public static String getExtension(final File file)
    {
        final String filename = file.getName();
        final int lastDotIndex = filename.lastIndexOf('.');

        if ((lastDotIndex < 0) || (lastDotIndex > (filename.length() - 1)))
        {
            // There is no extension, so return the empty string.
            return "";
        }

        return filename.substring(lastDotIndex + 1).toLowerCase(Locale.ROOT);
    }
}
