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
 * Unit tests for the {@link Portfolio} class.
 *
 * @since 0.1.0
 */
public class PortfolioTest
{
    private static Database database;

    /**
     * Loads the database "test.sqlite" and upgrading it if necessary.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @BeforeAll
    public static void setUp() throws Exception
    {
        database = new Database("test.sqlite");
        DatabaseUpgrader.upgrade(database);
    }

    /**
     * Asserts that when a new portfolio is saved, it gets a new ID from the
     * database, and that it's above zero.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_NewPortfolio_IdAboveZeroAfterSave() throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("BTC", "Bitcoin");
        currency.save(database);

        final Dao<Portfolio> portfolio = new Portfolio("Coinbase", currency);
        final int idAfterSave = portfolio.save(database);

        assertThat(idAfterSave, greaterThan(0));
    }

    /**
     * Asserts that when an existing portfolio is saved, it won't get a new ID
     * (meaning nothing is inserted into the database).
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_ExistingPortfolio_SameIdAfterSecondSave() throws Exception
    {
        // First, make sure that there are at least two currencies in the
        // database we can switch between.
        final Currency aud = new Currency("AUD", "Australian dollar");
        aud.save(database);
        final Currency gbp = new Currency("GBP", "British pound sterling");
        gbp.save(database);

        final Portfolio portfolio = new Portfolio("Forex", aud);
        final int idAfterFirstSave = portfolio.save(database);

        portfolio.setBaseCurrency(gbp.getId());
        final int idAfterSecondSave = portfolio.save(database);

        assertThat(idAfterSecondSave, is(idAfterFirstSave));
    }

    /**
     * Asserts that after saving a new portfolio, no exception is thrown when
     * trying to load the same portfolio from the database. That means that it
     * has been stored correctly in the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_ExistingPortfolio_CurrencyIsFound() throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("LTC", "Litecoin");
        currency.save(database);

        final Dao<Portfolio> newPortfolio = new Portfolio("Random", currency);
        final int createdId = newPortfolio.save(database);

        newPortfolio.load(createdId, database);
    }

    /**
     * Asserts that when trying to load a portfolio that does not exist in the
     * database, a {@link NoSuchElementException} is thrown.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_NotExistingPortfolio_ExceptionIsThrown()
    {
        // Negative row ID's will never exist in the database, so it's safe to
        // test exceptions with those.
        final int faultyId = -4;

        Assertions.assertThrows(NoSuchElementException.class, () ->
                new Portfolio("", 0).load(faultyId, database));
    }

    /**
     * Asserts that when loading all portfolios from an empty table, zero rows
     * are returned.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void LoadAll_EmptyTable_ZeroRecordsFound() throws Exception
    {
        // Empty the portfolio table.
        final Dao<Portfolio> portfolio = new Portfolio("", 0);
        portfolio.deleteAll(database);

        final List<Portfolio> portfolios = portfolio.loadAll(database);
        final int nPortfolios = portfolios.size();

        assertThat(nPortfolios, is(0));
    }

    /**
     * Asserts that when loading all portfolios from a table that is not empty,
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
        // Make sure we have at least one portfolio in the database.
        final Currency currency = new Currency("XLM", "Stellar");
        currency.save(database);
        final Dao<Portfolio> portfolio = new Portfolio("Friend", currency);
        portfolio.save(database);

        final int nPortfolios = portfolio.count(database);

        assertThat(nPortfolios, greaterThan(0));
    }

    /**
     * Asserts that when deleting a portfolio previously saved to the database,
     * it gets removed from the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Delete_ExistingPortfolio_RemovesOneRowFromTable()
            throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("CAD", "Canadian dollar");
        currency.save(database);

        final Dao<Portfolio> portfolioToDelete =
                new Portfolio("Delete", currency);
        portfolioToDelete.save(database);

        final int nPortfoliosBeforeDeletion = portfolioToDelete.count(database);
        portfolioToDelete.delete(database);
        final int nPortfoliosAfterDeletion = portfolioToDelete.count(database);

        assertThat(nPortfoliosAfterDeletion, is(nPortfoliosBeforeDeletion - 1));
    }

    /**
     * Asserts that when deleting a portfolio that hasn't been saved to the
     * database, nothing gets removed from the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Delete_UnsavedPortfolio_RemovesNothingFromTable()
            throws Exception
    {
        // Create a portfolio but don't save it to the database.
        final Dao<Portfolio> unsavedPortfolio = new Portfolio("Mistake", 0);

        final int nPortfoliosBeforeDeletion = unsavedPortfolio.count(database);
        unsavedPortfolio.delete(database);
        final int nPortfoliosAfterDeletion = unsavedPortfolio.count(database);

        assertThat(nPortfoliosAfterDeletion, is(nPortfoliosBeforeDeletion));
    }

    /**
     * Asserts that a call to {@link Portfolio#deleteAll(Database)} results in
     * an empty database table.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void DeleteAll_MethodCall_ZeroRecordsInTable() throws Exception
    {
        final Dao<Portfolio> portfolio = new Portfolio("Temp", 0);
        portfolio.deleteAll(database);

        final int nPortfolios = portfolio.count(database);

        assertThat(nPortfolios, is(0));
    }

    /**
     * Asserts that when adding portfolios to the database, the number of rows
     * in the database table is increased.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Count_AfterThreeNewPortfolios_AddsThreeMoreRows()
            throws Exception
    {
        final int nPortfoliosBeforeAdding =
                new Portfolio("", 0).count(database);

        final Dao<Portfolio> portfolio1 =
                new Portfolio("P1", new Currency("C1"));
        portfolio1.save(database);
        final Dao<Portfolio> portfolio2 =
                new Portfolio("P2", new Currency("C2"));
        portfolio2.save(database);
        final Dao<Portfolio> portfolio3 =
                new Portfolio("P3", new Currency("C3"));
        portfolio3.save(database);

        final int nPortfoliosAfterAdding = portfolio1.count(database);

        assertThat(nPortfoliosAfterAdding, is(3 + nPortfoliosBeforeAdding));
    }
}
