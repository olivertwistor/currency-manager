package nu.olivertwistor.currencymanager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SqlFileParser
{
    public SqlFileParser(final File file, final String delimiter)
            throws FileNotFoundException, IOException
    {
        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),
                        StandardCharsets.UTF_8)))
        {
            try (final Scanner scanner = new Scanner(bufferedReader))
            {

            }
        }
    }
}
