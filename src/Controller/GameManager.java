package Controller;

import Model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
/*
 * GENERAL NOTES
 * TODO where do unhandled exceptions (i.e. IllegalArgumentException) go?
 * TODO add causes to rethrown exceptions
 */

public class GameManager extends Application {
    public static void main(String[] args) {
        Application.launch(args); //Launching JavaFX application
    }

    public void start(Stage stage) {
        stage.setTitle("OOSE Quest II: TWOOSE");

        Player player = new Player();
        Shop shop = new Shop();

        ChanceEnemyFactory enemyGen = new ChanceEnemyFactory(setEnemyChances(), setEnemyChanceUpdates());

        StockManager stock = new FileStockManager("items.txt"); //TODO make this get file name from somewhere else?
        ShopManager shopManager = new ShopManager(shop, stock);
        IntermediateManager intermediate = new IntermediateManager(player, shopManager);

        //Equipping player with shop's cheapest gear to start adventure
        try {
            stock.loadStock();
        }
        catch (StockManagerException s) {
            System.out.println("Failed to start game, could not load items - " + s.getMessage()); //TODO what here?
        }

        shopManager.giveCheapestGear(player);

        //Starting main game loop
        boolean gameOver = false;
        boolean quit = false;
        while (!gameOver && !quit) {
            quit = intermediate.intermediateMenu();

            GameCharacter enemy = enemyGen.makeEnemy(); //FIXME can I get away with polymorphism here?
            BattleManager battle = new BattleManager(player, enemy);
            gameOver = battle.runBattle();
        }

        if (gameOver) {
            IntermediateManager.showStatistics();
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
