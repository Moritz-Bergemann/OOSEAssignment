package Model.Items;

import Model.ItemUser;

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

    public int getCost();

    public int getMinEffect();

    public int getMaxEffect();

    public void addToInventory(ItemUser itemUser) throws InventoryException;

    public void removeFromInventory(ItemUser itemUser) throws InventoryException;
}
