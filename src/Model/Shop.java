package Model;

import Controller.StockManager;
import Controller.StockManagerException;
import Model.Items.Armour;
import Model.Items.Item;
import Model.Items.Potion;
import Model.Items.Weapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Shop {
    private Set<Weapon> weaponStock;
    private Set<Armour> armourStock;
    private Set<Potion> potionStock;

    public Shop() {
        weaponStock = new HashSet<>();
        armourStock = new HashSet<>();
        potionStock = new HashSet<>();
    }

    /**
     * Loads in stock from external source and uses it to fill the shop inventory.
     * @param loader retrieves stock from external source
     */
    public void acquireStock(StockManager loader) {
        //Replacing inventory with that loaded from external source
        try {
            //Loading stock
            loader.loadStock();

            //Replacing individual item types with loaded stock
            weaponStock = loader.getLoadedWeapons();
            armourStock = loader.getLoadedArmour();
            potionStock = loader.getLoadedPotions();
        }
        catch (StockManagerException s) {
            System.out.println("Shop received stock manager exception");
            //TODO handle stock manager messing up
        }
    }

    /**
     * Retrieve the shop's entire stock as a set of items
     * @return the shop's entire stock
     */
    public Set<Item> getCurrentStock() {
        Set<Item> entireStock = new HashSet<>();

        entireStock.addAll(weaponStock);
        entireStock.addAll(armourStock);
        entireStock.addAll(potionStock);

        return Collections.unmodifiableSet(entireStock);
    }

    public Set<Weapon> getWeaponStock() {
        return Collections.unmodifiableSet(weaponStock);
    }

    public Set<Armour> getArmourStock() {
        return Collections.unmodifiableSet(armourStock);
    }

    public Set<Potion> getPotionStock() {
        return Collections.unmodifiableSet(potionStock);
    }
}
