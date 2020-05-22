package game.model.items;

import game.model.ItemUser;

/**
 * Interface for items that may be acquired/used by the player
 */
public interface Item {
    public String getName();

    public String getDescription();

    /** Gets the type of the item as a string.
     * NOTE: This is PURELY for user interface purposes and is never used as an 'instanceof' alternative -
     *  it simply communicates to the user what type the item is more clearly than the description does
     * @return item type as a string
     */
    public String getType();

    /**
     * @return cost of the item
     */
    public int getCost();

    /**
     * @return minimum effect of the item
     */
    public int getMinEffect();

    /**
     * @return maximum effect of the item
     */
    public int getMaxEffect();

    /**
     * Adds the item to the inventory of the imported item user (done as item users may keep track of different
     * types of items individually
     * @param itemUser item user to add item to
     * @throws InventoryException if item cannot be added
     */
    public void addToInventory(ItemUser itemUser) throws InventoryException;

    /**
     * Removes the item from the inventory of the imported item user
     * @param itemUser item user to remove item from
     * @throws InventoryException if item cannot be removed
     */
    public void removeFromInventory(ItemUser itemUser) throws InventoryException;

    /**
     * Creates a duplicate of the item
     * @return duplicate of item
     */
    public Item clone();
}
