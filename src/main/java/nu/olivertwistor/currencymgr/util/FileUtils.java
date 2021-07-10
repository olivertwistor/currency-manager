package nu.olivertwistor.currencymgr.util;

import com.adelean.inject.resources.core.InjectResources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

/**
 * Utilities for handling files and resources.
 *
 * @since //todo correct version
 */
public final class FileUtils
{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Loads a resource from a class path and parses it into a string.
     *
     * @param path  the path to the resource, relative to classpath
     * @param clazz the class of which class loader to load the resource
     *
     * @return The resource as a string.
     *
     * @throws NullPointerException if the resource couldn't be found.
     *
     * @since //todo correct version
     */
    public static String loadResourceToString(final String path,
                                              final Class<?> clazz)
    {
        LOG.trace("Loading resource to string.");

        return InjectResources.resource()
                .onClassLoaderOf(clazz)
                .withPath(path)
                .text(StandardCharsets.UTF_8);
    }

    /**
     * Empty constructor, used only to prevent instantiation.
     *
     * @since //todo correct version
     */
    private FileUtils()
    {
        // Just for preventing instantiation.
    }
}
