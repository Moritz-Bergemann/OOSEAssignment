package Model;

import Controller.Chance;

public abstract class Enemy extends Character {
    protected int minAttack;
    protected int maxAttack;
    protected int minDefense;
    protected int maxDefense;
    protected int gold;

    public Enemy(int health, int minAttack, int maxAttack, int minDefense, int maxDefense, int gold) {
        super(health);
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.minDefense = minDefense;
        this.maxDefense = maxDefense;
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
     * Logic for standard defense of enemy (no special abilities)
     * @return amount of damage for enemy's next defense
     */
    public int calcDefense() {
        return Chance.randBetween(minDefense, maxDefense);
    }

    //Getters
    public int getMinAttack() {
        return minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMinDefense() {
        return minDefense;
    }

    public int getMaxDefense() {
        return maxDefense;
    }

    public int getGold() {
        return gold;
    }
}
