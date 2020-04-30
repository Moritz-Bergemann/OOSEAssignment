package Model;

import Controller.StockManager;
import Controller.StockManagerException;
import Model.Items.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Shop {
    public Set<Item> stock;

    public Shop() {
        stock = new HashSet<>();
    }

    /**
     * Loads in stock from external source and uses it to fill the shop inventory.
     * @param loader retrieves stock from external source
     */
    public void acquireStock(StockManager loader) {
        //Replacing inventory with that loaded from external source
        try {
            Set<Item> inventory = loader.loadStock();
        }
        catch (StockManagerException s) {
            System.out.println("Shop received stock manager exception");
            //TODO handle stock manager messing up
        }
    }

    public Set<Item> getStock() {
        return Collections.unmodifiableSet(stock);
    }
}
