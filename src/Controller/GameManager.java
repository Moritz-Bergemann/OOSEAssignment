package Controller;

import Model.*;
import Model.Enemies.*;

import java.util.HashMap;
import java.util.Map;
/*
 * GENERAL NOTES
 * TODO where do unhandled exceptions (i.e. IllegalArgumentException) go?
 * TODO add causes to rethrown exceptions
 */

public class GameManager {
    public static void main(String[] args) {
        Player player = new Player();
        Shop shop = new Shop();

        ChanceEnemyFactory enemyGen = new ChanceEnemyFactory(setEnemyChances(), setEnemyChanceUpdates());

        StockManager stock = new FileStockManager("items.txt"); //TODO make this get file name from somewhere else?
        ShopManager shopManager = new ShopManager(shop, stock);
        MenuManager menus = new MenuManager(player, shopManager);

        shopManager.giveCheapestGear(player);

        //Starting main game loop
        boolean gameOver = false;
        boolean quit = false;
        while (!gameOver && !quit) {
            quit = menus.intermediateMenu();

            GameCharacter enemy = enemyGen.makeEnemy(); //FIXME can I get away with polymorphism here?
            BattleManager battle = new BattleManager(player, enemy);
            gameOver = battle.runBattle();
        }

        if (gameOver) {
            MenuManager.showStatistics();
        }
    }

    /**
     * Sets up initial probabilities for spawning of enemies
     * @return Map of enemy names and corresponding probabilities
     */
    public static Map<String, Double> setEnemyChances() {
        Map<String, Double> enemyChances = new HashMap<>();

        enemyChances.put("Slime", 0.5);
        enemyChances.put("Goblin", 0.30);
        enemyChances.put("Ogre", 0.2);
        enemyChances.put("Dragon", 0.0);

        return enemyChances;
    }

    /**
     * Sets amounts by which probabilites of spawning enemies should change each battle
     * @return Map of enemy names and corresponding probability updates
     */
    public static Map<String, Double> setEnemyChanceUpdates() {
        Map<String, Double> enemyChanceUpdates = new HashMap<>();

        enemyChanceUpdates.put("Slime", -0.05);
        enemyChanceUpdates.put("Goblin", -0.05);
        enemyChanceUpdates.put("Ogre", -0.05);
        enemyChanceUpdates.put("Dragon", 0.15);
    }
}
