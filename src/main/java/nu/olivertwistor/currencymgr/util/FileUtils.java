package nu.olivertwistor.currencymgr.util;

import com.adelean.inject.resources.core.InjectResources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

public final class FileUtils
{
    private static final Logger LOG = LogManager.getLogger();

    public static String loadResourceToString(final String path,
                                              final Class<?> clazz)
    {
        LOG.trace("Loading resource to string.");

        return InjectResources.resource()
                .onClassLoaderOf(clazz)
                .withPath(path)
                .text(StandardCharsets.UTF_8);
    }

    private FileUtils()
    {
        // Just for preventing instantiation.
    }
}
