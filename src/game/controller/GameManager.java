package game.controller;

import game.controller.battle.BattleManager;
import game.controller.shop.FileStockManager;
import game.controller.shop.ShopException;
import game.controller.shop.ShopManager;
import game.controller.shop.StockManager;
import game.model.*;
import game.view.BattleMenu;
import game.view.IntermediateMenu;
import game.view.MenuUtils;
import game.view.ShopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

//TODO make potions only work on the required character
//TODO make dragon victory less scuffed

/**
 * Overall manager for game, contains main() method.
 * Creates all relevant classes and controls flow of game.
 * While some of the other controllers are individually even-driven, this primary controller is procedural.
 */
public class GameManager extends Application {
    public static void main(String[] args) {
        Application.launch(args); //Launching JavaFX application
    }

    public void start(Stage mainStage) {
        try {
            runGame(mainStage);
        }
        catch (GameException g) {
            MenuUtils.showError("Game Error", "Failed to run game - " + g.getMessage(), mainStage);
        }
        catch (Exception e) {
            MenuUtils.showError("Unknown Error", "An unexpected exception has occurred - " + e.getMessage(),
                    mainStage);
        }
    }

    public void runGame(Stage mainStage) throws GameException {
        mainStage.setTitle("OOSE Quest II: TWOOSE");

        mainStage.setScene(new Scene(new VBox(new Text("OOSE Quest II: TWOOSE"))));

        Player player = new Player();

        ChanceEnemyFactory enemyGen = new ChanceEnemyFactory(setEnemyChances(), setEnemyChanceUpdates());

        //Creating managers for game components
        StockManager stock = new FileStockManager("items.txt");
        ShopMenu shopMenu = new ShopMenu(mainStage, stock, player);
        ShopManager shopManager = new ShopManager(stock, shopMenu);
        IntermediateMenu intermediateMenu = new IntermediateMenu(mainStage, player);
        IntermediateManager intermediate = new IntermediateManager(player, shopManager, intermediateMenu);

        //Equipping player with shop's cheapest gear to start adventure
        try {
            shopManager.giveCheapestGear(player);
        }
        catch (ShopException s) {
            throw new GameException("Failed to give cheapest gear - " + s.getMessage(), s);
        }

        //Starting main game loop
        boolean gameOver = false;
        boolean quit = false;
        while (!gameOver && !quit) {
            //Running intermediate menu (including option to go to shop)
            quit = intermediate.runIntermediate();

            if (!quit) {
                //Running battle
                GameCharacter enemy = enemyGen.makeEnemy();
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
