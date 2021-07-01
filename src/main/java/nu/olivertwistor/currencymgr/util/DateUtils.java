package nu.olivertwistor.currencymgr.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils
{
    public static String getDate(final LocalDate date)
    {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getToday()
    {
        return getDate(LocalDate.now());
    }
}
