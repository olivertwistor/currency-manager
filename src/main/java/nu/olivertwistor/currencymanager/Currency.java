package nu.olivertwistor.currencymanager;

import org.jetbrains.annotations.NonNls;

/**
 * This class describes a currency, for example SEK or USD. Can also be a
 * crypto currency, such as BTC or LTC.
 *
 * @since  0.1.0
 */
@SuppressWarnings("ClassWithoutLogger")
public final class Currency
{
    private final int id;
    private final String name;

    /**
     * Creates a new currency object.
     *
     * @param name name of the currency; can be either ticker or long name, but
     *             please be consistent over all currencies
     *
     * @since 0.1.0
     */
    public Currency(final String name)
    {
        this(0, name);
    }

    /**
     * Creates a new currency object.
     *
     * @param id   row ID in the database
     * @param name name of the currency; can be either ticker or long name, but
     *             please be consistent over all currencies
     *
     * @since 0.1.0
     */
    @SuppressWarnings("SameParameterValue")
    private Currency(final int id, final String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public @NonNls String toString()
    {
        return "Currency{id=" + this.id + ", name='" + this.name + '}';
    }
}
