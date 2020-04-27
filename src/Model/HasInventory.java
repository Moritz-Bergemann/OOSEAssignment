package Model;

import java.util.Set;

/**
 * Interface for classes that possess an inventory of items.
 */
public interface HasInventory {
    public Set<Item> getItemSet();

    public Set<Weapon> getWeaponSet();

    public Set<Armour> getArmourSet();

    public Set<Potion> getPotionSet();

    public int getNumItems();

    public void addItem(Item item) throws InventoryException;

    public void removeItem(Item item) throws InventoryException;
}
