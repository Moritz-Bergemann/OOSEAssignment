package Model;
import Model.Observers.HealthChangeObserver;

import java.lang.Math;
import java.util.LinkedList;
import java.util.List;

/** Class representing a character with health that can attack and defend within the game.
 */
public abstract class GameCharacter {
    protected int maxHealth; //Maximum health of character
    protected int health; //Current health of character

    protected List<HealthChangeObserver> healthChangeObservers; //Observers to notify when health change occurs

    /**
     * Creates a new character with the input character health
     * @param health Maximum/starting health of character
     */
    public GameCharacter(int health) {
        //Asserting health not less than 0
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }

        //Initialising character health to imported value & giving them full health
        this.maxHealth = health;
        this.health = health;

        healthChangeObservers = new LinkedList<>();
    }

    /**
     * Reduces the character's health by the imported value (or to at most 0)
     * @param lostHealth
     */
    public void loseHealth(int lostHealth) {
        health = Math.max(0, health - lostHealth);

        notifyHealthChangeObservers(health);
    }

    /**
     * Increases the character's health by the imported amount (or to at most maxHealth)
     * @param gainedHealth
     */
    public void gainHealth(int gainedHealth) {
        health = Math.min(maxHealth, health + gainedHealth);

        notifyHealthChangeObservers(health);
    }

    /**
     * @return whether or not character is currently alive
     */
    public boolean isAlive() {
        return (health > 0);
    }

    /**
     * Calculates the amount of damage the character should do for their next attack
     * @return amount of damage to do
     */
    public abstract int calcAttack();

    /**
     * Calculates the amount of Defence the character should have against an oncoming attack
     * @return amount of Defence against attack
     */
    public abstract int calcDefence();

    //GETTERS
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Retrieves character name.
     * @return character name
     */
    public abstract String getName();

    /**
     * @return character's minimum attack
     */
    public abstract int getMinAttack();

    /**
     * @return character's maximum attack
     */
    public abstract int getMaxAttack();

    /**
     * @return character's minimum defence
     */
    public abstract int getMinDefence();

    /**
     * @return character's maximum defence
     */
    public abstract int getMaxDefence();

    /**
     * @return character's gold
     */
    public abstract int getGold();


    public void addHealthChangeObserver(HealthChangeObserver observer) {
        healthChangeObservers.add(observer);
    }

    public void removeHealthChangeObserver(HealthChangeObserver observer) {
        healthChangeObservers.remove(observer);
    }

    public void notifyHealthChangeObservers(int newHealth) {
        for (HealthChangeObserver observer : healthChangeObservers) {
            observer.notify(newHealth);
        }
    }
}