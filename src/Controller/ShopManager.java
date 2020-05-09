package Controller;

import Model.ItemUser;
import Model.Items.*;
import Model.Player;
import Model.Shop;

/*TODO should buying an item create a duplicate of it? Or is it fine to have multiple items in the player's inventory
    refer to the same base object?
 */
//TODO should shop operations catch & rethrow ANY exception to ensure gold amounts remain accurate? CHECK WITH TUTOR

public class ShopManager {
    private StockManager stockLoader; //Loads items from shop
    private Shop shop; //Shop model object containing inventory

    public ShopManager(Shop shop, StockManager stockLoader) {
        this.stockLoader = stockLoader;
        this.shop = shop;
    }

    /**
     * Provides the imported item user with the cheapest gear available in the shop.
     * @param user Item user to equip gear with
     */
    public void giveCheapestGear(ItemUser user) {
        //Loading shop
        shop.acquireStock(stockLoader);

        //TODO figure this shit out

        for (Item item : shop.getCurrentStock()) {

        }
    }

    public void runShop(Player player) {
        //Re-acquiring stock for shop (in case has been updated)
        shop.acquireStock(stockLoader);

        //TODO create observers here

        ShopMenu.run(); //TODO put observers here and stuff
    }

    public void purchaseItem(Item item, Player player) {
        int initialGold = player.getGold();

        try {
            //Making player pay cost of item (throws exception if insufficient gold)
            player.loseGold(item.getCost());

            //Adding item to player's inventory (throws exception if insufficient inventory capacity)
            item.addToInventory(player);

            ShopMenu.showMessage(String.format("You bought %s!", item.getName()));
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            ShopMenu.showMessage("Could not purchase item - " + inv.getMessage());
        }
    }

    public void sellItem(Item item, Player player) {
        int initialGold = player.getGold();

        try {
            //Giving player half of item's cost in gold
            player.gainGold(item.getCost() / 2);

            //Removing item from player's inventory (throws exception if item is currently equipped) //TODO test this
            item.removeFromInventory(player);

            ShopMenu.showMessage(String.format("You bought %s!", item.getCost()));
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            ShopMenu.showMessage("Could not purchase item - " + inv.getMessage());
        }
    }

    public void purchaseEnchantment(Player player, Weapon weapon, String enchantmentName) {
        int initialGold = player.getGold();

        try {
            int cost = EnchantmentFactory.getEnchantmentCost(enchantmentName);

            //Making player pay cost of item (throws exception if insufficient gold)
            player.loseGold(cost);

            //Applying enchantment to imported weapon
            EnchantmentFactory.applyEnchantment(enchantmentName, weapon);
        }
        catch (InventoryException inv) {
            //Resetting player's gold amount
            player.setGold(initialGold);

            ShopMenu.showMessage("Could not apply enchantment - " + inv.getMessage());
        }
    }
}
