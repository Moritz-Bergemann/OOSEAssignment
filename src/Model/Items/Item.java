package Model.Items;

import Model.ItemUser;

/**
 * Interface for items that may be acquired/used by the player
 */
public interface Item {
    public String getName();

    public String getDescription();

    public int getCost();

    public int getMinEffect();

    public int getMaxEffect();

    public void addToInventory(ItemUser itemUser) throws InventoryException;

    public void removeFromInventory(ItemUser itemUser) throws InventoryException;

    //TODO add getDescription message?
}
