package nu.olivertwistor.currencymgr.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileUtilsTest
{
    @Test
    public void loadResourceToString_existingResource_doesNotThrowException()
    {
        FileUtils.loadResourceToString(
                "/db/0/create-table-dbversion.sql", FileUtilsTest.class);
    }

    @Test
    public void loadResourceToString_nonexistingResource_throwsException()
    {
        assertThrows(NullPointerException.class, () ->
                FileUtils.loadResourceToString("this-path-doesnt-exist",
                        FileUtilsTest.class));
    }
}
