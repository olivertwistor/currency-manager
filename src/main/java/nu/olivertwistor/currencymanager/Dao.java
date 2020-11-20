package nu.olivertwistor.currencymanager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Provides methods for CRUD operations.
 *
 * {@link #get(int)} and {@link #getAll()} provide Read functionality;
 * {@link #save(Object)} provides both Create and Update functionality;
 * {@link #delete(Object)} provides Delete functionality.
 *
 * @param <T> data objects that needs to be persisted in the database
 *
 * @author Johan Nilsson
 * @since  0.1.0
 */
public interface Dao<T>
{
    /**
     * Gets an object if it exists in the database.
     *
     * @param id row ID
     *
     * @return The object if it exists, wrapped in an {@link Optional}.
     *
     * @throws SQLException if there was some kind of SQL error
     *
     * @since 0.1.0
     */
    Optional<T> get(int id) throws SQLException;

    /**
     * Gets a list of all objects of this type in the database.
     *
     * @return List of all objects.
     *
     * @since 0.1.0
     */
    List<T> getAll();

    /**
     * Saves an object to the database. If it doesn't exist, a new record in
     * the database should be inserted; if it already exist, its corresponding
     * record in the database should be updated.
     *
     * What's considered already existing must be defined in the implementing
     * class.
     *
     * @param t the object to save
     *
     * @since 0.1.0
     */
    void save(T t);

    /**
     * Deletes an object from the database. The implementing class must define
     * how to find the corresponding record in the database.
     *
     * @param t the object to delete
     *
     * @since 0.1.0
     */
    void delete(T t);
}
