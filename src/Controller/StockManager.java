package Controller;

import Model.Items.Armour;
import Model.Items.Item;
import Model.Items.Potion;
import Model.Items.Weapon;

import java.util.Set;

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
     * @return The set of all loaded items
     * @throws StockManagerException if loadStock() has not been called
     */
    public Set<Item> getLoadedStock() throws StockManagerException;

    /**
     * Gets all weapons currently loaded by the StockManager
     * @return The set of all loaded weapons
     * @throws StockManagerException if loadStock() has not been called
     */
    public Set<Weapon> getLoadedWeapons() throws StockManagerException;

    /**
     * Gets all items currently loaded by the StockManager
     * @return The set of all loaded armour
     * @throws StockManagerException if loadStock() has not been called
     */
    public Set<Armour> getLoadedArmour() throws StockManagerException;

    /**
     * Gets all items currently loaded by the StockManager
     * @return The set of all loaded potions
     * @throws StockManagerException if loadStock() has not been called
     */
    public Set<Potion> getLoadedPotions() throws StockManagerException;

}
