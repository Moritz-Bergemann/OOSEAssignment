package Testing;

import Model.*;
import Model.Items.Armour;
import Model.Items.InventoryException;
import Model.Items.Item;
import Model.Items.Weapon;
import Controller.*;

public class ModelTest {
    public static void main(String[] args) {
        Player player = new Player();
        Shop shop = new Shop();

        System.out.println("Loading items from shop");

        StockManager stockManager = new FileStockManager("TestItems.txt");

        //Loading stock into shop
        shop.acquireStock(stockManager);

        System.out.println("\nITEMS IN SHOP:");
        for (Item item : shop.getStock()) {
            try {
                //Describing item in shop
                System.out.println("=======================");
                System.out.println("Name: " + item.getName());
                System.out.println("Description: " + item.getDescription());
                System.out.println(String.format("Min-Max Effect: %s-%s", item.getMinEffect(), item.getMaxEffect()));
                System.out.println("=======================");

                //Adding item from shop to player
                item.addToInventory(player);
            }
            catch (InventoryException i) {
                System.out.println("Inventory Exception - " + i.getMessage());
            }
        }

        System.out.println("PLAYER ITEMS:");
        String outStr = "";
        outStr += "Weapons:";
        for (Item i : player.getWeaponSet()) {
            outStr += i.getName() + ",";
        }
        outStr += "\nArmour:";
        for (Item i : player.getArmourSet()) {
            outStr += i.getName() + ",";
        }
        System.out.println("\nPotions:");
        for (Item i : player.getPotionSet()) {
            outStr += i.getName() + ",";
        }
        System.out.println(outStr);

        System.out.println("\nLAYER INTERACTION TESTS");
        //Making player equip first weapon & first armour
        Weapon eWeapon = player.getWeaponSet().iterator().next();
        Armour eArmour = player.getArmourSet().iterator().next();
        System.out.println(String.format("Player - equipping %s weapon and %s armour", eWeapon.getName(), eArmour.getName()));

        player.setWeapon(eWeapon);
        player.setArmour(eArmour);

        System.out.println(String.format("Player attack: %s-%s", player.getMinAttack(), player.getMaxAttack()));
        System.out.println(String.format("Player defence: %s-%s", player.getMinDefence(), player.getMaxDefence()));

        System.out.println("Calculating player attack 3 times");
        for (int ii = 0; ii < 3; ii++) {
            System.out.println(player.calcAttack());
        }
        System.out.println("Calculating player defence 3 times");
        for (int ii = 0; ii < 3; ii++) {
            System.out.println(player.calcDefence());
        }
    }
}
