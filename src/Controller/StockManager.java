package Controller;

import Model.Items.Armour;
import Model.Items.Item;
import Model.Items.Potion;
import Model.Items.Weapon;

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
    public List<Item> getLoadedStock() throws StockManagerException;

    /**
     * Gets all weapons currently loaded by the StockManager
     * @return The List of all loaded weapons
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Weapon> getLoadedWeapons() throws StockManagerException;

    /**
     * Gets all items currently loaded by the StockManager
     * @return The List of all loaded armour
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Armour> getLoadedArmour() throws StockManagerException;

    /**
     * Gets all items currently loaded by the StockManager
     * @return The List of all loaded potions
     * @throws StockManagerException if loadStock() has not been called
     */
    public List<Potion> getLoadedPotions() throws StockManagerException;

}
