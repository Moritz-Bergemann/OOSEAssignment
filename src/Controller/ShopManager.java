package Controller;

import Model.Items.*;
import Model.Player;
import Model.Shop;
import View.ShopMenu;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/*TODO should buying an item create a duplicate of it? Or is it fine to have multiple items in the player's inventory
    refer to the same base object?
 */
//TODO should shop operations catch & rethrow ANY exception to ensure gold amounts remain accurate? CHECK WITH TUTOR

public class ShopManager {
    private StockManager stockLoader; //Loads items from shop
    private Shop shop; //Shop model object containing inventory
    private ShopMenu shopMenu;

    public ShopManager(Shop shop, StockManager stockLoader, ShopMenu shopMenu) {
        this.stockLoader = stockLoader;
        this.shop = shop;
        this.shopMenu = shopMenu;
    }

    /**
     * Provides the imported player with the cheapest gear available in the shop.
     *
     * @param player Player to equip gear with
     */
    public void giveCheapestGear(Player player) {
        //Loading shop
        shop.acquireStock(stockLoader);

        //Getting cheapest weapon & cheapest armour using Comparator
        Weapon cheapestWeapon = Collections.min(shop.getWeaponStock(),
                Comparator.comparing(Item::getCost));
        Armour cheapestArmour = Collections.min(shop.getArmourStock(),
                Comparator.comparing(Item::getCost));

        //Adding cheapest weapon & armour to player's inventory
        try {
            cheapestWeapon.addToInventory(player);
            cheapestArmour.addToInventory(player);
        }
        catch (InventoryException inv) {
            throw new IllegalArgumentException("Failed to add cheapest weapon and armour",
                    inv); //TODO make sure this is right for final design
        }

        //Making player equip cheapest weapon & armour FIXME should we do this?
        player.setCurWeapon(cheapestWeapon);
        player.setCurArmour(cheapestArmour);
    }

    public void runShop(Player player) { //FIXME unused import here
        //Re-acquiring stock for shop (in case has been updated)
        shop.acquireStock(stockLoader);

        shopMenu.setManager(this);

        shopMenu.runMenu();
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

        System.out.println("DEBUG player gold after purchase - " + player.getGold());
    }

    public void sellItem(Player player, Item item) {
        int initialGold = player.getGold();

        try {
            //Giving player half of item's cost in gold
            player.gainGold(item.getCost() / 2);

            //Removing item from player's inventory (throws exception if item is currently equipped) //TODO test this
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
     * @param player          Player buying enchantment
     * @param weapon          weapon to enchant
     * @param enchantmentName name of enchantment to add
     */
    public void purchaseEnchantment(Player player, Weapon weapon, String enchantmentName) {
        int initialGold = player.getGold();

        try {
            int cost = EnchantmentFactory.getEnchantmentCost(enchantmentName);

            //Making player pay cost of item (throws exception if insufficient gold)
            player.loseGold(cost);

            //Applying enchantment to imported weapon
            EnchantmentFactory.applyEnchantment(enchantmentName, weapon);

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
    public Set<String> getAllEnchantmentNames() {
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
