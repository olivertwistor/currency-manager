package nu.olivertwistor.currencymanager.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * Unit tests for the {@link Currency} class.
 *
 * @since 0.1.0
 */
public class CurrencyTest
{
    private static Database database;

    /**
     * Loads the database "test.sqlite".
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @BeforeAll
    public static void setUp() throws Exception
    {
        database = new Database("test.sqlite");
    }

    /**
     * Asserts that when a new currency is saved, it gets a new ID from the
     * database, and that it's above zero.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_NewCurrency_IdAboveZeroAfterSave() throws Exception
    {
        final Dao<Currency> currency = new Currency("SEK");
        final int idAfterSave = currency.save(database);

        assertThat(idAfterSave, greaterThan(0));
    }

    /**
     * Asserts that when an existing currency is saved, it won't get a new ID
     * (meaning nothing is inserted into the database).
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_ExistingCurrency_SameIdAfterSecondSave() throws Exception
    {
        final Currency currency = new Currency("USD");
        final int idAfterFirstSave = currency.save(database);

        currency.setDescription("Amerikansk dollar");
        final int idAfterSecondSave = currency.save(database);

        assertThat(idAfterSecondSave, is(idAfterFirstSave));
    }

    /**
     * Asserts that after saving a new currency, no exception is thrown when
     * trying to load the same currency from the database. That means that it
     * has been stored correctly in the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_ExistingCurrency_CurrencyIsFound() throws Exception
    {
        final Dao<Currency> newCurrency = new Currency("NOK");
        final int createdId = newCurrency.save(database);

        newCurrency.load(createdId, database);
    }

    /**
     * Asserts that when trying to load a currency that does not exist in the
     * database, a {@link NoSuchElementException} is thrown.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_NotExistingCurrency_ExceptionIsThrown()
    {
        // Negative row ID's will never exist in the database, so it's safe to
        // test exceptions with those.
        final int faultyId = -4;

        Assertions.assertThrows(NoSuchElementException.class, () ->
                new Currency("").load(faultyId, database));
    }

    /**
     * Asserts that when loading all currencies from an empty table, zero rows
     * are returned.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void LoadAll_EmptyTable_ZeroRecordsFound() throws Exception
    {
        // Empty the currency table.
        final Dao<Currency> currency = new Currency("");
        currency.deleteAll(database);

        final List<Currency> currencies = currency.loadAll(database);
        final int nCurrencies = currencies.size();

        assertThat(nCurrencies, is(0));
    }

    /**
     * Asserts that when loading all currencies from a table that is not empty,
     * more than zero rows are returned.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void LoadAll_NonEmptyTable_MoreThanZeroRecordsFound()
            throws Exception
    {
        // Make sure we have at least one currency in the database.
        final Dao<Currency> currency = new Currency("EUR");
        currency.save(database);

        final int nCurrencies = currency.count(database);

        assertThat(nCurrencies, greaterThan(0));
    }

    /**
     * Asserts that when deleting a currency previously saved to the database,
     * it gets removed from the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Delete_ExistingCurrency_RemovesOneRowFromTable()
            throws Exception
    {
        final Dao<Currency> currencyToDelete = new Currency("CHF");
        currencyToDelete.save(database);

        final int nCurrenciesBeforeDeletion = currencyToDelete.count(database);
        currencyToDelete.delete(database);
        final int nCurrenciesAfterDeletion = currencyToDelete.count(database);

        assertThat(nCurrenciesAfterDeletion, is(nCurrenciesBeforeDeletion - 1));
    }

    /**
     * Asserts that when deleting a currency that hasn't been saved to the
     * database, nothing gets removed from the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Delete_UnsavedCurrency_RemovesNothingFromTable()
            throws Exception
    {
        // Create a currency but don't save it to the database.
        final Dao<Currency> unsavedCurrency = new Currency("CHF");

        final int nCurrenciesBeforeDeletion = unsavedCurrency.count(database);
        unsavedCurrency.delete(database);
        final int nCurrenciesAfterDeletion = unsavedCurrency.count(database);

        assertThat(nCurrenciesAfterDeletion, is(nCurrenciesBeforeDeletion));
    }

    /**
     * Asserts that a call to {@link Currency#deleteAll(Database)} results in
     * an empty database table.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void DeleteAll_MethodCall_ZeroRecordsInTable() throws Exception
    {
        final Dao<Currency> currency = new Currency("");
        currency.deleteAll(database);

        final int nCurrencies = currency.count(database);

        assertThat(nCurrencies, is(0));
    }

    /**
     * Asserts that when adding currencies to the database, the number of rows
     * in the database table is increased.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Count_AfterThreeNewCurrencies_AddsThreeMoreRows()
            throws Exception
    {
        final int nCurrenciesBeforeAdding = new Currency("").count(database);

        final Dao<Currency> currency1 = new Currency("C1");
        currency1.save(database);
        final Dao<Currency> currency2 = new Currency("C2");
        currency2.save(database);
        final Dao<Currency> currency3 = new Currency("C3");
        currency3.save(database);

        final int nCurrenciesAfterAdding = currency1.count(database);

        assertThat(nCurrenciesAfterAdding, is(3 + nCurrenciesBeforeAdding));
    }
}
