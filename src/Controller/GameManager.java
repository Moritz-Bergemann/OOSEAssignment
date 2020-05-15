package Controller;

import Model.*;
import View.BattleMenu;
import View.IntermediateMenu;
import View.MenuUtils;
import View.ShopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

    public void start(Stage mainStage) {
        try {
            runGame(mainStage);
        } //TODO change exception messages from println to JavaFX related
        catch (GameException g) {
            System.out.println("Failed to run game - " + g.getMessage());
        }
//        catch (Exception e) {
//            System.out.println("An unexpected exception has occurred - " + e.getMessage());
//        }
    }

    public void runGame(Stage mainStage) throws GameException {
        mainStage.setTitle("OOSE Quest II: TWOOSE");

        mainStage.setScene(new Scene(new VBox(new Text("OOSE Quest II: TWOOSE"))));

        Player player = new Player();
        Shop shop = new Shop();

        ChanceEnemyFactory enemyGen = new ChanceEnemyFactory(setEnemyChances(), setEnemyChanceUpdates());

        //Creating managers for game components
        StockManager stock = new FileStockManager("items.txt"); //TODO make this get file name from somewhere else?
        ShopMenu shopMenu = new ShopMenu(mainStage, shop, player);
        ShopManager shopManager = new ShopManager(shop, stock, shopMenu);
        IntermediateMenu intermediateMenu = new IntermediateMenu(mainStage, player);
        IntermediateManager intermediate = new IntermediateManager(player, shopManager, intermediateMenu);

        //Equipping player with shop's cheapest gear to start adventure
        try {
            stock.loadStock();
        }
        catch (StockManagerException s) {
            throw new GameException("Could not load items - " + s.getMessage(), s);
        }
        shopManager.giveCheapestGear(player);

        //Starting main game loop
        boolean gameOver = false;
        boolean quit = false;
        while (!gameOver && !quit) {
            //Running intermediate menu (including option to go to shop)
            quit = intermediate.intermediateMenu();

            if (!quit) {
                //Running battle
                GameCharacter enemy = enemyGen.makeEnemy(); //FIXME can I get away with polymorphism here?
                BattleMenu battleMenu = new BattleMenu(player, enemy, mainStage);
                BattleManager battle = new BattleManager(player, enemy, battleMenu);
                gameOver = battle.runBattle();

                //Updating enemy chances for next battle (if it occurs)
                enemyGen.updateChances();
            }
        }

        if (gameOver) {
            //Showing the menu for the game ending (victory menu if player won, defeat menu if player died)
            MenuUtils.gameEndedMenu(player);
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

        return enemyChanceUpdates;
    }
}
