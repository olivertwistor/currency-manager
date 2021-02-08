package nu.olivertwistor.currencymanager.util;

import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.util.Locale;

public final class FileUtils
{
    @NonNls
    public static final String DATABASE_EXTENSION = "sqlite";

    public static String getExtension(final File file)
    {
        final String filename = file.getName();
        final int extensionIndex = filename.lastIndexOf(".");

        if (extensionIndex > 0 && extensionIndex <filename.length() - 1)
        {
            final String extension = filename.substring(extensionIndex + 1);
            return extension.toLowerCase(Locale.getDefault());
        }

        return "";
    }

    public static File addExtension(final File file, final String extension)
    {
        final String originalFilename = file.getAbsolutePath();
        final String addedExtensionFilename =
                originalFilename + "." + extension;

        return new File(addedExtensionFilename);
    }

    private FileUtils()
    {

    }
}
