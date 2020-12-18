package nu.olivertwistor.currencymanager.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class UpgraderTest
{
    private static File sqlFile;

    @BeforeAll
    static void setUp() throws URISyntaxException
    {
        final URL url = UpgraderTest.class.getResource("/0_to_1.sql");
        sqlFile = Paths.get(url.toURI()).toFile();
    }
}
