package Controller;

import Model.*;
import Model.Enemies.*;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    public static void main(String[] args) {
        Player player = new Player();
        ChanceEnemyFactory enemyGen = new ChanceEnemyFactory(setEnemyChances(), setEnemyChanceUpdates());

        //Starting main game loop
        boolean gameOver = false;
        boolean quit = false;

        while (!gameOver && !quit) {
            quit = MenuManager.intermediateMenu(player);

            GameCharacter enemy = enemyGen.makeEnemy(); //FIXME can I get away with polymorphism here?
            gameOver = BattleManager.runBattle(player, enemy);
        }

        if (gameOver) {
            MenuManager.showStatistics(player);
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

    public static Map<String, Double> setEnemyChanceUpdates() {
        Map<String, Double> enemyChanceUpdates = new HashMap<>();

        enemyChanceUpdates.put("Slime", -0.05);
        enemyChanceUpdates.put("Goblin", -0.05);
        enemyChanceUpdates.put("Ogre", -0.05);
        enemyChanceUpdates.put("Dragon", 0.15);
    }
}
