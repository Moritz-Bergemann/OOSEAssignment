package Model;

import Controller.StockManager;
import Controller.StockManagerException;
import Model.Items.Armour;
import Model.Items.Item;
import Model.Items.Potion;
import Model.Items.Weapon;

import java.util. ArrayList;
import java.util.List;

//FIXME is this class even necessary?

public class Shop {
    private List<Weapon> weaponStock;
    private List<Armour> armourStock;
    private List<Potion> potionStock;

    public Shop() {
        weaponStock = new  ArrayList<>();
        armourStock = new  ArrayList<>();
        potionStock = new  ArrayList<>();
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
    public List<Item> getCurrentStock() {
        List<Item> entireStock = new  ArrayList<>();

        entireStock.addAll(weaponStock);
        entireStock.addAll(armourStock);
        entireStock.addAll(potionStock);

        return entireStock;
    }

    public List<Weapon> getWeaponStock() {
        return weaponStock;
    }

    public List<Armour> getArmourStock() {
        return armourStock;
    }

    public List<Potion> getPotionStock() {
        return potionStock;
    }
}
