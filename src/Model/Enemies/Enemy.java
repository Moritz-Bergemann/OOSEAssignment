package Model.Enemies;

import Controller.Chance;
import Model.GameCharacter;

public abstract class Enemy extends GameCharacter {
    protected int minAttack;
    protected int maxAttack;
    protected int minDefence;
    protected int maxDefence;
    protected int gold;

    public Enemy(int health, int minAttack, int maxAttack, int minDefence, int maxDefence, int gold) {
        super(health);
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.minDefence = minDefence;
        this.maxDefence = maxDefence;
        this.gold = gold;
    }

    /**
     * Logic for standard attack of enemy (no special abilities)
     * @return amount of damage for enemy's next attack
     */
    public int calcAttack() {
        return Chance.randBetween(minAttack, maxAttack);
    }

    /**
     * Logic for standard Defence of enemy (no special abilities)
     * @return amount of damage for enemy's next Defence
     */
    public int calcDefence() {
        return Chance.randBetween(minDefence, maxDefence);
    }

    //Getters
    public int getMinAttack() {
        return minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMinDefence() {
        return minDefence;
    }

    public int getMaxDefence() {
        return maxDefence;
    }

    public int getGold() {
        return gold;
    }
}
