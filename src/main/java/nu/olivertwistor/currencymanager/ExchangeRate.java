package nu.olivertwistor.currencymanager;

import org.jetbrains.annotations.NonNls;

import java.time.LocalDate;

/**
 * This class describes an exchange rate between two currencies on a specific
 * date.
 *
 * @since  0.1.0
 */
final class ExchangeRate
{
    private final LocalDate date;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final double rate;

    /**
     * Creates a new exchange rate object.
     *
     * For example,
     * {@code
     *     new ExchangeRate(
     *      LocalDate.of(2020, 11, 23),
     *      new Currency("EUR"),
     *      new Currency("SEK"),
     *      10.14
     *     );
     * }
     * describes that on November 23, 2020, 1 EUR was worth 10.14 SEK.
     *
     * @param date           the date of this exchange rate
     * @param baseCurrency   The currency the exchange rate is based upon
     * @param targetCurrency The currency that's relative to the base currency
     * @param rate           the number of target currency per base currency
     *
     * @since 0.1.0
     */
    ExchangeRate(final LocalDate date,
                 final Currency baseCurrency,
                 final Currency targetCurrency,
                 final double rate)
    {
        this.date = date;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public LocalDate getDate()
    {
        return this.date;
    }

    public Currency getBaseCurrency()
    {
        return this.baseCurrency;
    }

    public Currency getTargetCurrency()
    {
        return this.targetCurrency;
    }

    public double getRate()
    {
        return this.rate;
    }

    @Override
    public @NonNls String toString()
    {
        return "ExchangeRate{" +
                "date=" + this.date + ", " +
                "baseCurrency=" + this.baseCurrency + ", " +
                "targetCurrency=" + this.targetCurrency + ", " +
                "rate=" + this.rate +
                '}';
    }
}
