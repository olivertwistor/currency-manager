package nu.olivertwistor.currencymanager.menus;

/**
 * This interface declares the three behaviours common for all menus in this
 * application: define, show, and act. The menu is {@link #define() defined} by
 * adding menu items. In {@link #act(int)} the actions for each menu item are
 * defined, and whether the action would entail going up a menu level (or quit
 * the application) is returned. The menu can be {@link #show(boolean) shown}
 * to the user and a user choice in form av an integer is returned.
 *
 * @since 0.1.0
 */
public interface Menu
{
    /**
     * Defines the menu items, for example using a {@link java.util.Map} of
     * integers as keys (to work well with {@link #show(boolean)}) and string
     * values for menu item descriptions.
     *
     * @since 0.1.0
     */
    void define();

    /**
     * Performs an action depending on which menu item that was selected.
     *
     * @param menuItem the menu item as an integer (for example a key in a menu
     *                 item map)
     *
     * @return Whether the selected action should lead to going up one menu
     *         level (true) or continue on the same level (false).
     *
     * @since 0.1.0
     */
    boolean act(int menuItem);

    /**
     * Shows this menu to the user, and requests a response for which menu item
     * to select.
     *
     * @param allowInvalid whether this method should allow input that's not an
     *                     existing menu item (true) or loop until an existing
     *                     menu item has been selected (false)
     *
     * @return The selected menu item as an integer.
     *
     * @since 0.1.0
     */
    int show(boolean allowInvalid);
}
