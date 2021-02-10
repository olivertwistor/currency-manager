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
 * Unit tests for the {@link Wallet} class.
 *
 * @since 0.1.0
 */
public class WalletTest
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
     * Asserts that when a new wallet is saved, it gets a new ID from the
     * database, and that it's above zero.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_NewWallet_IdAboveZeroAfterSave() throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("BTC", "Bitcoin");
        currency.save(database);

        final Dao<Wallet> wallet = new Wallet(currency, currency);
        final int idAfterSave = wallet.save(database);

        assertThat(idAfterSave, greaterThan(0));
    }

    /**
     * Asserts that when an existing wallet is saved, it won't get a new ID
     * (meaning nothing is inserted into the database).
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Save_ExistingWallet_SameIdAfterSecondSave() throws Exception
    {
        // First, make sure that there are at least two currencies in the
        // database we can switch between.
        final Currency aud = new Currency("AUD", "Australian dollar");
        aud.save(database);
        final Currency gbp = new Currency("GBP", "British pound sterling");
        gbp.save(database);

        final Wallet wallet = new Wallet(aud, aud);
        final int idAfterFirstSave = wallet.save(database);

        wallet.setBaseCurrency(gbp.getId());
        final int idAfterSecondSave = wallet.save(database);

        assertThat(idAfterSecondSave, is(idAfterFirstSave));
    }

    /**
     * Asserts that after saving a new wallet, no exception is thrown when
     * trying to load the same wallet from the database. That means that it
     * has been stored correctly in the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_ExistingWallet_CurrencyIsFound() throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("LTC", "Litecoin");
        currency.save(database);

        final Dao<Wallet> newWallet = new Wallet(currency, currency);
        final int createdId = newWallet.save(database);

        newWallet.load(createdId, database);
    }

    /**
     * Asserts that when trying to load a wallet that does not exist in the
     * database, a {@link NoSuchElementException} is thrown.
     *
     * @since 0.1.0
     */
    @Test
    public void Load_NotExistingWallet_ExceptionIsThrown()
    {
        // Negative row ID's will never exist in the database, so it's safe to
        // test exceptions with those.
        final int faultyId = -4;

        Assertions.assertThrows(NoSuchElementException.class, () ->
                new Wallet(0, 0).load(faultyId, database));
    }

    /**
     * Asserts that when loading all wallets from an empty table, zero rows
     * are returned.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void LoadAll_EmptyTable_ZeroRecordsFound() throws Exception
    {
        // Empty the wallet table.
        final Dao<Wallet> wallet = new Wallet(0, 0);
        wallet.deleteAll(database);

        final List<Wallet> wallets = wallet.loadAll(database);
        final int nWallets = wallets.size();

        assertThat(nWallets, is(0));
    }

    /**
     * Asserts that when loading all wallets from a table that is not empty,
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
        // Make sure we have at least one wallet in the database.
        final Currency currency = new Currency("XLM", "Stellar");
        currency.save(database);
        final Dao<Wallet> wallet = new Wallet(currency, currency);
        wallet.save(database);

        final int nWallets = wallet.count(database);

        assertThat(nWallets, greaterThan(0));
    }

    /**
     * Asserts that when deleting a wallet previously saved to the database,
     * it gets removed from the database.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Delete_ExistingWallet_RemovesOneRowFromTable()
            throws Exception
    {
        // First, make sure that there is at least one currency in the
        // database we can use.
        final Currency currency = new Currency("CAD", "Canadian dollar");
        currency.save(database);

        final Dao<Wallet> walletToDelete = new Wallet(currency, currency);
        walletToDelete.save(database);

        final int nWalletsBeforeDeletion = walletToDelete.count(database);
        walletToDelete.delete(database);
        final int nWalletsAfterDeletion = walletToDelete.count(database);

        assertThat(nWalletsAfterDeletion, is(nWalletsBeforeDeletion - 1));
    }

    /**
     * Asserts that when deleting a wallet that hasn't been saved to the
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
        // Create a wallet but don't save it to the database.
        final Dao<Wallet> unsavedWallet = new Wallet("Mistake", 0, 0);

        final int nWalletsBeforeDeletion = unsavedWallet.count(database);
        unsavedWallet.delete(database);
        final int nWalletsAfterDeletion = unsavedWallet.count(database);

        assertThat(nWalletsAfterDeletion, is(nWalletsBeforeDeletion));
    }

    /**
     * Asserts that a call to {@link Wallet#deleteAll(Database)} results in
     * an empty database table.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void DeleteAll_MethodCall_ZeroRecordsInTable() throws Exception
    {
        final Dao<Wallet> wallet = new Wallet(0, 0);
        wallet.deleteAll(database);

        final int nWallets = wallet.count(database);

        assertThat(nWallets, is(0));
    }

    /**
     * Asserts that when adding wallets to the database, the number of rows
     * in the database table is increased.
     *
     * @throws Exception if anything went wrong.
     *
     * @since 0.1.0
     */
    @Test
    public void Count_AfterThreeNewWallets_AddsThreeMoreRows()
            throws Exception
    {
        final int nWalletsBeforeAdding =
                new Wallet(0, 0).count(database);

        final Dao<Wallet> wallet1 =
                new Wallet("P1", new Currency("C1"), new Currency("C1"));
        wallet1.save(database);
        final Dao<Wallet> wallet2 =
                new Wallet("P2", new Currency("C2"), new Currency("C2"));
        wallet2.save(database);
        final Dao<Wallet> wallet3 =
                new Wallet("P3", new Currency("C3"), new Currency("C3"));
        wallet3.save(database);

        final int nWalletsAfterAdding = wallet1.count(database);

        assertThat(nWalletsAfterAdding, is(3 + nWalletsBeforeAdding));
    }
}
