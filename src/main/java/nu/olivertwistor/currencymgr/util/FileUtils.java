package nu.olivertwistor.currencymgr.util;

import com.adelean.inject.resources.core.InjectResources;
import org.jetbrains.annotations.NonNls;

import java.nio.charset.StandardCharsets;

public final class FileUtils
{
    public static String loadResourceToString(@NonNls final String path,
                                              final Class<?> clazz)
    {
        return InjectResources.resource()
                .onClassLoaderOf(clazz)
                .withPath(path)
                .text(StandardCharsets.UTF_8);
    }
}
