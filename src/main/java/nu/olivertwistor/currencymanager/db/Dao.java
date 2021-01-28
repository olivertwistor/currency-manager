package nu.olivertwistor.currencymanager.db;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classes implementing this interface can perform CRUD operations on a database. The create and update operations are rolled into one method, {@link #save(Database)}.
 *
 * @param <T> any class that is represented in a database
 *
 * @since 0.1.0
 */
interface Dao<T>
{
    /**
     * Saves this object to the provided database. If a record already exists
     * in the database, it's updated. Otherwise, a new record is inserted.
     *
     * @param database the database to save to
     *
     * @return Row ID of the saved object.
     *
     * @throws SQLException if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    int save(Database database) throws SQLException;

    /**
     * Loads an object of this class, with the provided row ID, from the
     * provided database.
     *
     * @param id       row ID of the record to get
     * @param database the database to load from
     *
     * @return An object of this class.
     *
     * @throws NoSuchElementException if the desired record couldn't be found.
     * @throws SQLException           if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    T load(int id, Database database) throws SQLException;

    /**
     * Loads all records corresponding to this class, from the provided
     * database, and returns them as object of this class.
     *
     * @param database the database to load from
     *
     * @return A list of object of this class.
     *
     * @throws SQLException if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    List<T> loadAll(Database database) throws SQLException;

    /**
     * Deletes the record from the provided database, that corresponds with
     * this object.
     *
     * @param database the database to delete from
     *
     * @throws SQLException if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    void delete(Database database) throws SQLException;

    /**
     * Deletes all records corresponding to this class, from the provided
     * database, that corresponds with this object.
     *
     * NOTE: This removes every row in the table(s) corresponding to this class.
     *
     * @param database the database to delete from
     *
     * @throws SQLException if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    void deleteAll(Database database) throws SQLException;

    /**
     * Counts the number of records corresponding to this class in the database.
     *
     * @param database the database to read from
     *
     * @return The numer of records corresponding to this class.
     *
     * @throws SQLException if anything went wrong with the database.
     *
     * @since 0.1.0
     */
    int count(Database database) throws SQLException;
}
