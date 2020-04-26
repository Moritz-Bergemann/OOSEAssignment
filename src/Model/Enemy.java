package Model;

import Controller.Chance;

public abstract class Enemy extends Character {
    protected int minAttack;
    protected int maxAttack;
    protected int minDefense;
    protected int maxDefense;
    protected int gold;

    /**
     * Creates a new enemy character with undefined characteristics
     * (MUST BE DEFINED IN INHERITING CLASS)
     */
    public Enemy() {
        super(0); //Health set statically here for readability when overriding in subclass
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
