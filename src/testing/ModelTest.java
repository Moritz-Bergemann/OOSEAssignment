//package Testing;
//
//import OOSEGame.Model.*;
//import OOSEGame.Model.Items.Armour;
//import OOSEGame.Model.Items.InventoryException;
//import OOSEGame.Model.Items.Item;
//import OOSEGame.Model.Items.Weapon;
//import OOSEGame.Controller.*;
//import OOSEGame.View.ShopMenu;
//
//public class ModelTest {
//    public static void main(String[] args) {
//        Player player = new Player();
//
//        System.out.println("Loading items from shop");
//
//        StockManager stockManager = new FileStockManager("TestItems.txt");
//
//        //Loading stock into shop
//
//        System.out.println("\nITEMS IN SHOP:");
//        for (Item item : st.getCurrentStock()) {
//            try {
//                //Describing item in shop
//                System.out.println("=======================");
//                System.out.println("Name: " + item.getName());
//                System.out.println("Description: " + item.getDescription());
//                System.out.println(String.format("Min-Max Effect: %s-%s", item.getMinEffect(), item.getMaxEffect()));
//                System.out.println("Cost: " + item.getCost());
//                System.out.println("=======================");
//
//                //Adding item from shop to player
//                item.addToInventory(player);
//            }
//            catch (InventoryException i) {
//                System.out.println("Inventory Exception - " + i.getMessage());
//            }
//        }
//
//        System.out.println("PLAYER ITEMS:");
//        String outStr = "";
//        outStr += "Weapons:";
//        for (Item i : player.getWeaponList()) {
//            outStr += i.getName() + ",";
//        }
//        outStr += "\nArmour:";
//        for (Item i : player.getArmourList()) {
//            outStr += i.getName() + ",";
//        }
//        outStr += ("\nPotions:");
//        for (Item i : player.getPotionList()) {
//            outStr += i.getName() + ",";
//        }
//        System.out.println(outStr);
//
//        System.out.println("\nLAYER INTERACTION TESTS");
//        //Making player equip first weapon & first armour
//        Weapon eWeapon = player.getWeaponList().iterator().next();
//        Armour eArmour = player.getArmourList().iterator().next();
//        System.out.println(String.format("Player - equipping %s weapon and %s armour", eWeapon.getName(), eArmour.getName()));
//
//        player.setCurWeapon(eWeapon);
//        player.setCurArmour(eArmour);
//
//        System.out.println(String.format("Player attack: %s-%s", player.getMinAttack(), player.getMaxAttack()));
//        System.out.println(String.format("Player defence: %s-%s", player.getMinDefence(), player.getMaxDefence()));
//
//        System.out.println("Calculating player attack 10 times");
//        for (int ii = 0; ii < 10; ii++) {
//            System.out.println(player.calcAttack());
//        }
//        System.out.println("Calculating player defence 10 times");
//        for (int ii = 0; ii < 10; ii++) {
//            System.out.println(player.calcDefence());
//        }
//        System.out.println();
//
//        System.out.println("Clearing player...");
//        player = new Player();
//
//        ShopMenu shopMenu = null;
//        ShopManager shopManager = new ShopManager(shop, stockManager, shopMenu);
//
//        System.out.println("Giving player cheapest weapon and armour...");
//        shopManager.giveCheapestGear(player);
//
//        System.out.println("Player's current weapons: " + player.getWeaponList());
//        System.out.println("Player's current armour: " + player.getArmourList());
//        System.out.println("Player's current potions: " + player.getPotionList());
//    }
//}