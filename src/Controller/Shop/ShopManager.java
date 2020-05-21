package Controller.Shop;

import Controller.EnchantmentFactory;
import Model.Items.*;
import Model.Player;
import View.ShopMenu;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * (Pseudo) event-driven manager for the shop that the player can visit.
 * Displays the shop's user interface (which calls this class' methods to perform shop operations)
 */
public class ShopManager {
    private StockManager stockLoader; //Loads items from shop
    private ShopMenu shopMenu;

    public ShopManager(StockManager stockLoader, ShopMenu shopMenu) {
        this.stockLoader = stockLoader;
        this.shopMenu = shopMenu;
    }

    /**
     * Provides the imported player with the cheapest gear available in the shop.
     *
     * @param player Player to equip gear with
     */
    public void giveCheapestGear(Player player) throws ShopException {
        //Loading shop
        try {
            stockLoader.loadStock();
        }
        catch (StockManagerException s) {
            throw new ShopException("Failed to load stock cheapest stock", s);
        }

        //Getting cheapest weapon & cheapest armour using Comparator
        Weapon cheapestWeapon = Collections.min(stockLoader.getLoadedWeapons(),
                Comparator.comparing(Item::getCost));
        Armour cheapestArmour = Collections.min(stockLoader.getLoadedArmour(),
                Comparator.comparing(Item::getCost));

        //Adding cheapest weapon & armour to player's inventory
        try {
            cheapestWeapon.addToInventory(player);
            cheapestArmour.addToInventory(player);
        }
        catch (InventoryException inv) {
            throw new ShopException("Failed to add cheapest weapon and armour to player inventory",
                    inv);
        }

        //Making player equip cheapest weapon & armour
        player.setCurWeapon(cheapestWeapon);
        player.setCurArmour(cheapestArmour);
    }

    public void runShop() throws ShopException {
        //Re-acquiring stock for shop (in case has been updated)
        try {
            stockLoader.loadStock();
        }
        catch (StockManagerException s) {
            throw new ShopException("Could not get shop inventory", s);
        }

        shopMenu.setManager(this);

        shopMenu.showMenu();
    }

    public void purchaseItem(Item item, Player player) {
        int initialGold = player.getGold();

        try {
            //Making player pay cost of item (throws exception if insufficient gold)
            player.loseGold(item.getCost());

            //Adding item to player's inventory (throws exception if insufficient inventory capacity)

            item.addToInventory(player);

            shopMenu.showMessage(String.format("You bought '%s' for %d gold!", item.getName(), item.getCost()));
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            shopMenu.showMessage("Could not purchase item - " + inv.getMessage());
        }
    }

    public void sellItem(Player player, Item item) {
        int initialGold = player.getGold();

        try {
            //Giving player half of item's cost in gold
            player.gainGold(item.getCost() / 2);

            //Removing item from player's inventory (throws exception if item is currently equipped)
            item.removeFromInventory(player);

            shopMenu.showMessage(String.format("You sold '%s' for %d gold!", item.getName(), item.getCost() / 2));
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            shopMenu.showMessage("Could not purchase item - " + inv.getMessage());
        }
    }

    /**
     * Enchants a weapon with the enchantment decorator identified by a string. Throws exception if string does
     * not correlate to a known enchantment
     *
     * @param player Player buying enchantment
     * @param weapon weapon to enchant
     * @param enchantmentName name of enchantment to add
     */
    public void purchaseEnchantment(Player player, Weapon weapon, String enchantmentName) {
        int initialGold = player.getGold();

        try {
            int cost = EnchantmentFactory.getEnchantmentCost(enchantmentName);

            //Making player pay cost of item (throws exception if insufficient gold)
            player.loseGold(cost);


            //Applying enchantment to imported weapon
            Weapon enchantedWeapon = EnchantmentFactory.applyEnchantment(enchantmentName, weapon);

            //Removing original weapon from set & re-adding enchanted weapon
            player.removeWeapon(weapon);
            player.addWeapon(enchantedWeapon);

            shopMenu.showMessage(String.format("You bought '%s' for %d gold!", enchantmentName, cost));
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            shopMenu.showMessage("Could not apply enchantment - " + inv.getMessage());
        }
    }

    /**
     * Calls Enchantment Factory for information on enchantment names (to avoid enchantmentMenu requiring knowledge
     *  about the factory)
     * @return set of all enchantment names
     */
    public List<String> getAllEnchantmentNames() {
        return EnchantmentFactory.getAllEnchantmentNames();
    }

    /**
     * Calls Enchantment Factory for enchantment description (to avoid enchantmentMenu requiring knowledge
     * about the factory)
     * @param name name of enchantment
     * @return enchantment description
     */
    public String getEnchantmentDescription(String name) {
        return EnchantmentFactory.getEnchantmentDescription(name);
    }

    /**
     * Calls Enchantment Factory for enchantment cost (to avoid enchantmentMenu requiring knowledge
     * about the factory)
     * @param name name of enchantment
     * @return enchantment cost
     */
    public int getEnchantmentCost(String name) {
        return EnchantmentFactory.getEnchantmentCost(name);
    }
}
