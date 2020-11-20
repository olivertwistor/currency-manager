package nu.olivertwistor.currencymanager;

/**
 * This class describes a currency, for example SEK or USD. Can also be a
 * crypto currency, such as BTC or LTC.
 *
 * @author Johan Nilsson
 * @since  0.1.0
 */
public class Currency
{
    private int id;
    private String name;

    /**
     * Creates a new currency object.
     *
     * @param id   row ID in the database
     * @param name name of the currency; can be either ticker or long name, but
     *             please be consistent over all currencies
     *
     * @since 0.1.0
     */
    public Currency(final int id, final String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates a new currency object.
     *
     * @since 0.1.0
     */
    public Currency()
    {
        this(-1, "");
    }

    /**
     * Gets the row ID of this object in the database.
     *
     * @return Row ID.
     *
     * @since 0.1.0
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets the row ID of this object in the database.
     *
     * @param id row ID
     *
     * @since 0.1.0
     */
    public void setId(final int id)
    {
        this.id = id;
    }

    /**
     * Gets the name of this currency.
     *
     * @return The name of this currency.
     *
     * @since 0.1.0
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name of this currency.
     *
     * @param name name of this currency; can be either ticker or long name,
     *             but please be consistent over all currencies
     *
     * @since 0.1.0
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Currency{id=" + this.id + ", name='" + this.name + "}";
    }
}
