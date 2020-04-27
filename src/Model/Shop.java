package Model;

import java.util.HashSet;
import java.util.Set;

public class Shop implements HasInventory {
    public Set<Item> stock;

    public Shop() {
        stock = new HashSet<>();
    }

    /**
     * Loads in stock from external source and uses it to fill the shop inventory.
     * @param loader retrieves stock from external source
     */
    public void acquireStock(StockLoader loader) {
        //Replacing inventory with that loaded from external source
        inventory = loader.loadStock();
    }

    @Override
    public Set<Item> getItemSet() {
        return null;
    }

    @Override
    public Set<Weapon> getWeaponSet() {
        return null;
    }

    @Override
    public Set<Armour> getArmourSet() {
        return null;
    }

    @Override
    public Set<Potion> getPotionSet() {
        return null;
    }

    @Override
    public int getNumItems() {
        return 0;
    }

    @Override
    public void addItem(Item item) throws InventoryException {

    }

    @Override
    public void removeItem(Item item) throws InventoryException {

    }
}
