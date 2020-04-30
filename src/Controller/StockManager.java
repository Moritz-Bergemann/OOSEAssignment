package Controller;

import Model.Items.Item;

import java.util.Set;

/**
 * Interface for managing stock of items for the store from external sources/
 */
public interface StockManager {
    public Set<Item> loadStock() throws StockManagerException;
}
