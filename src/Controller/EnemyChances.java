package Controller;

import Model.Enemies.Enemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyChances {
    private Map<String, Double> chances; //Chances to spawn different enemies
    private Map<String, Double> chanceUpdates; //Amounts by which chances change after each battle

    public EnemyChances(Map<String, Double> chances, Map<String, Double> chanceUpdates) {
        //TODO check if chances & chance updates correspond to each other

        this.chances = chances;
        this.chanceUpdates = chanceUpdates;
    }

    public Enemy makeEnemy() { //TODO

    }

    /**
     * Updates the spawn chances of each enemy according to the 'updates' map
     */
    public void updateChances() {
        for (String enemy : chanceUpdates.keySet()) {
        }
    }
}
