package game.controller;

import game.model.enemies.*;

import java.util.Map;

/**
 * Factory for creating enemies based on the contained chances.
 */
public class ChanceEnemyFactory {
    private Map<String, Double> chances; //Chances to spawn different enemies
    private Map<String, Double> chanceUpdates; //Amounts by which chances change after each battle

    public ChanceEnemyFactory(Map<String, Double> chances, Map<String, Double> chanceUpdates) {
        if (!chances.keySet().equals(chanceUpdates.keySet())) {
            throw new IllegalArgumentException("Sets do not contain equal keys");
        }

        this.chances = chances;
        this.chanceUpdates = chanceUpdates;
    }

    /**
     * Returns a new enemy randomly depending on the current state of this object's probabilities.
     * @return selected enemy
     */
    public Enemy makeEnemy() { //TODO add commenting
        double randChance = Math.random();
        double chanceHit = 0.0;
        String chosenEnemy = null;

        for (String enemyName : chances.keySet()) {
            chanceHit += chances.get(enemyName);

            if (chanceHit > randChance) {
                chosenEnemy = enemyName;
                break;
            }
        }

        if (chosenEnemy == null) {
            throw new IllegalArgumentException("Chances do not sum correctly");
        }

        Enemy enemy = makeFromString(chosenEnemy);

        return enemy;
    }

    /**
     * Updates the spawn chances of each enemy according to the 'updates' map
     */
    public void updateChances() {
        for (String enemy : chanceUpdates.keySet()) {
            Double newChance = chances.get(enemy) + chanceUpdates.get(enemy);

            //Giving updated chance ceiling of 1.0 & floor of 0.0
            if (newChance > 1.0) {
                newChance = 1.0;
            }
            else if (newChance < 0.0) {
                newChance = 0.0;
            }

            chances.put(enemy, newChance);
        }
    }

    /**
     * Creates an enemy based on the chosen name
     * @param name name of the enemy to be chosen
     * @return chosen enemy
     */
    public Enemy makeFromString(String name) {
        Enemy newEnemy;

        switch (name) {
            case "Slime":
                newEnemy = new SlimeEnemy();
                break;
            case "Goblin":
                newEnemy = new GoblinEnemy();
                break;
            case "Ogre":
                newEnemy = new OgreEnemy();
                break;
            case "Dragon":
                newEnemy = new DragonEnemy();
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid enemy parameter '%s' provided", name));
        }

        return newEnemy;
    }
}
