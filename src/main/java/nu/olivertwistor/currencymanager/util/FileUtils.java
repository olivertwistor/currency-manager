package nu.olivertwistor.currencymanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.util.Locale;

/**
 * This utility class contains various static methods for files.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"unused", "StaticMethodOnlyUsedInOneClass",
        "ClassOnlyUsedInOnePackage"})
public final class FileUtils
{
    @NonNls
    private static final Logger LOG = LogManager.getLogger(FileUtils.class);

    /**
     * The file extension for database files (including a preceding dot).
     *
     * @since 0.1.0
     */
    @NonNls
    public static final String DATABASE_EXTENSION = ".sqlite";

    /**
     * Gets the extension from a provided file.
     *
     * @param file the file from which name to extract the extension
     *
     * @return The file extension; or an empty string if the provided file has
     *         no extension.
     *
     * @since 0.1.0
     */
    public static String getExtension(final File file)
    {
        final String filename = file.getName();
        String extension = "";
        final int extensionIndex = filename.lastIndexOf('.'); //NON-NLS

        if ((extensionIndex > 0) && (extensionIndex < (filename.length() - 1)))
        {
            extension = filename.substring(extensionIndex + 1);
            extension = extension.toLowerCase(Locale.getDefault());
        }

        LOG.debug("Filename: {}; extension: {}", filename, extension);

        return extension;
    }

    /**
     * Adds a provided extension to the filename of a provided file.
     *
     * @param file      the file to which to add the extension
     * @param extension the file extension to add to the file
     *
     * @return A file pointing to the provided file but with an added extension.
     *
     * @since 0.1.0
     */
    public static File addExtension(final File file,
                                    final @NonNls String extension)
    {
        final String originalFilename = file.getAbsolutePath();
        final String addedExtensionFilename = originalFilename + extension;
        final File newFile = new File(addedExtensionFilename);

        LOG.debug("Added {} to {}, resulting in {}.",
                extension, originalFilename, newFile.getAbsolutePath());

        return newFile;
    }

    /**
     * Empty private constructor to prevent instantiated.
     *
     * @since 0.1.0
     */
    private FileUtils() { }
}
