package game.controller.shop;

import game.model.items.Armour;
import game.model.items.Item;
import game.model.items.Potion;
import game.model.items.Weapon;

import java.util.List;

/**
 * Interface for managing stock of items for the store from external sources/
 */
public interface StockManager {
    /**
     * Loads all items from the stock manager's source, effectively 'updating' the stock held by the StockManager
     *  with any stock changes
     * @throws StockManagerException if stock manager fails to load stock
     */
    public void loadStock() throws StockManagerException;

    /**
     * Gets all items currently loaded by the StockManager
     * @return The List of all loaded items
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Item> getLoadedStock();

    /**
     * Gets all weapons currently loaded by the StockManager
     * @return The List of all loaded weapons
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Weapon> getLoadedWeapons();

    /**
     * Gets all items currently loaded by the StockManager
     * @return The List of all loaded armour
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Armour> getLoadedArmour();

    /**
     * Gets all items currently loaded by the StockManager
     * @return The List of all loaded potions
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Potion> getLoadedPotions();

}
