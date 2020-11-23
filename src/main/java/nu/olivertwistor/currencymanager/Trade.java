package nu.olivertwistor.currencymanager;

import java.time.LocalDate;

/**
 * This class describes a trade (e.g. a buy, sell, or something more esoteric).
 * A trade happens on a date, and has a currency, an amount (positive for a
 * buy, negative for a sell), a price (per unit) and an optional fee. Both the
 * price and the fee are represented in the specified currency.
 *
 * @since 0.1.0
 */
public class Trade
{
    private final int id;
    private final LocalDate date;
    private final Currency currency;
    private final double amount;
    private final double price;
    private final double fee;

    /**
     * Creates a new trade object.
     *
     * @param date     date when the trade happened
     * @param currency the currency this trade is about
     * @param amount   the number of units of currency in this trade, e.g.
     *                 bought (+) or sold (-)
     * @param price    the price point for one unit of currency
     * @param fee      fee for this trade, in the specified currenct
     *
     * @since 0.1.0
     */
    public Trade(final LocalDate date,
                 final Currency currency,
                 final double amount,
                 final double price,
                 final double fee)
    {
        this(0, date, currency, amount, price, fee);
    }

    /**
     * Creates a new trade object.
     *
     * @param id       row ID in the database
     * @param date     date when the trade happened
     * @param currency the currency this trade is about
     * @param amount   the number of units of currency in this trade, e.g.
     *                 bought (+) or sold (-)
     * @param price    the price point for one unit of currency
     * @param fee      fee for this trade, in the specified currenct
     *
     * @since 0.1.0
     */
    @SuppressWarnings("SameParameterValue")
    private Trade(final int id,
                  final LocalDate date,
                  final Currency currency,
                  final double amount,
                  final double price,
                  final double fee)
    {
        this.id = id;
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.price = price;
        this.fee = fee;
    }

    public int getId()
    {
        return this.id;
    }

    public LocalDate getDate()
    {
        return this.date;
    }

    public Currency getCurrency()
    {
        return this.currency;
    }

    public double getAmount()
    {
        return this.amount;
    }

    public double getPrice()
    {
        return this.price;
    }

    public double getFee()
    {
        return this.fee;
    }

    @Override
    public String toString()
    {
        return "Trade{" +
                "id=" + this.id + ", " +
                "date=" + this.date + ", " +
                "currency=" + this.currency + ", " +
                "amount=" + this.amount + ", " +
                "price=" + this.price + ", " +
                "fee=" + this.fee +
                "}";
    }
}
