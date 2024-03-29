package game.model;


import game.model.items.*;
import game.model.observers.*;

import java.util.*;

/**
 * Class for player character within the game. Posses their own inventory, and has equipped weapons/armour that
 * their attack/defense.
 */
public class Player extends GameCharacter implements ItemUser {
    private static final int startingHealth = 30; //Starting health of player
    private static final int startingGold = 100; //Starting gold of player
    private static final int startingItemCapacity = 15; //Starting item capacity of player

    private String name;
    private final List<Weapon> weaponSet;
    private final List<Armour> armourSet;
    private final List<Potion> potionSet;
    int itemCapacity;
    private Weapon curWeapon;
    private Armour curArmour;
    private int gold;

    private boolean wonGame;

    //Observers
    private List<NameChangeObserver> nameChangeObservers;
    private List<WeaponChangeObserver> weaponChangeObservers;
    private List<ArmourChangeObserver> armourChangeObservers;
    private List<GoldChangeObserver> goldChangeObservers;

    /**
     * Default constructor
     * NOTE: name, weapon and armour variables should be initialised before character can attempt a battle
     */
    public Player() {
        super(startingHealth);
        name = null;
        weaponSet = new  ArrayList<>();
        armourSet = new  ArrayList<>();
        potionSet = new  ArrayList<>();
        itemCapacity = startingItemCapacity;
        curWeapon = null;
        curArmour = null;
        gold = startingGold;
        wonGame = false;

        //Setting up observers
        nameChangeObservers = new LinkedList<>();
        goldChangeObservers = new LinkedList<>();
        healthChangeObservers = new LinkedList<>();
        weaponChangeObservers = new LinkedList<>();
        armourChangeObservers = new LinkedList<>();
    }

    /**
     * @return currently equipped weapon
     */
    public Weapon getCurWeapon() {
        return curWeapon;
    }

    /**
     * @return currently equipped armour
     */
    public Armour getCurArmour() {
        return curArmour;
    }

    /**
     * Sets the characters weapon
     * @param weapon weapon for character to equip
     */
    public void setCurWeapon(Weapon weapon) {
        this.curWeapon = weapon;

        notifyWeaponChangeObservers(weapon);
    }

    /**
     * Sets the characters weapon
     * @param armour armour for character to equip
     */
    public void setCurArmour(Armour armour) {
        this.curArmour = armour;

        notifyArmourChangeObservers(armour);
    }

    /**
     * Updates the character's name (initially null)
     * @param name new name for character
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        else if (name.strip() == "") {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.name = name;

        notifyNameChangeObservers(this.name);
    }

    /**
     * Get the number of items currently held by the player
     * @return number of items player holds
     */
    public int getNumItems() {
        return weaponSet.size() + armourSet.size() + potionSet.size();
    }

    public int getGold() {
        return gold;
    }

    /**
     * Adds the imported amount of gold
     * @param addedGold amount of gold to add
     */
    public void gainGold(int addedGold) {
        gold += addedGold;

        notifyGoldChangeObservers(gold);
    }

    /**
     * Removes the imported amount of gold (throws InventoryException if would reduce gold to below zero)
     * @param lostGold amount of gold to lose
     */
    public void loseGold(int lostGold) throws InventoryException {
        if (lostGold > this.gold) {
            throw new InventoryException(String.format("Cannot lose %d gold (only has %d)",
                    lostGold, this.gold));
        }

        gold -= lostGold;

        notifyGoldChangeObservers(gold);
    }

    /**
     * Sets the player's gold to the imported value.
     * Should only be used if gainGold or loseGold are inappropriate.
     * @param gold new value for gold
     */
    public void setGold(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException("Cannot set gold to negative value");
        }

        this.gold = gold;

        notifyGoldChangeObservers(gold);
    }

    /**
     * Make the player indicate that they have won the game.
     */
    public void setWonGame() {
        wonGame = true;
    }

    public boolean wonGame() {
        return wonGame;
    }

    /**
     * Determines whether the player has been appropriately set to be ready for battle
     * @return whether the character is ready for battle
     */
    public boolean readyForBattle() {
        //Checking whether player name is null/empty, weapon is null & armour is null (all must be false
        //   for player to be ready for battle
        if (name == null) {
            return false;
        }
        else {
            return !name.strip().equals("")
                && curWeapon != null
                && curArmour != null;
        }
    }

    //Inherited from 'Character'
    @Override
    public int calcAttack() {
        return curWeapon.calcAttack();
    }

    @Override
    public int calcDefence() {
        return curArmour.calcDefence();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMinAttack() {
        return curWeapon.getMinEffect();
    }

    @Override
    public int getMaxAttack() {
        return curWeapon.getMaxEffect();
    }

    @Override
    public int getMinDefence() {
        return curArmour.getMinEffect();
    }

    @Override
    public int getMaxDefence() {
        return curArmour.getMaxEffect();
    }

    //Inherited from 'ItemUser'
    @Override
    public void addWeapon(Weapon weapon) throws InventoryException {
        if (getNumItems() >= itemCapacity) {
            throw new InventoryException("Inventory is full!");
        }
        weaponSet.add(weapon);
    }

    @Override
    public void addArmour(Armour armour) throws InventoryException {
        if (getNumItems() >= itemCapacity) {
            throw new InventoryException("Inventory is full!");
        }
        armourSet.add(armour);
    }

    @Override
    public void addPotion(Potion potion) throws InventoryException {
        if (getNumItems() >= itemCapacity) {
            throw new InventoryException("Inventory is full!");
        }
        potionSet.add(potion);
    }

    @Override
    public List<Weapon> getWeaponList() {
        return weaponSet;
    }

    @Override
    public List<Armour> getArmourList() {
        return armourSet;
    }

    @Override
    public List<Potion> getPotionList() {
        return potionSet;
    }

    /**
     * Gets the list of all weapons not currently equipped
     * @return all unequipped weapons
     */
    public List<Weapon> getUnequippedWeaponList() {
        List<Weapon> weaponList = new LinkedList<>();

        for (Weapon weapon : weaponSet) {
            if (weapon != curWeapon) {
                weaponList.add(weapon);
            }
        }

        return weaponList;
    }

    /**
     * Gets the list of all armour not currently equipped
     * @return all unequipped armour
     */
    public List<Armour> getUnequippedArmourList() {
        List<Armour> armourList = new LinkedList<>();

        for (Armour armour : armourSet) {
            if (armour != curArmour) {
                armourList.add(armour);
            }
        }

        return armourList;
    }

    @Override
    public List<Item> getItemSet() {
        List<Item> allItems = new  ArrayList<>();

        allItems.addAll(weaponSet);
        allItems.addAll(armourSet);
        allItems.addAll(potionSet);

        return allItems;
    }

    @Override
    public void removeWeapon(Weapon weapon) throws InventoryException {
        if (weapon == curWeapon) { /*NOTE: '==' used over '.equals()' since checking if the imported weapon is the SAME
            object, not just one with the same attributes*/
            throw new InventoryException("Cannot remove current weapon");
        }

        //Removing from inventory if it is the same weapon instance as the one to remove
        boolean present = weaponSet.removeIf(e -> e == weapon);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }

    @Override
    public void removeArmour(Armour armour) throws InventoryException {
        if (armour == curArmour) { //Same as removeWeapon note
            throw new InventoryException("Cannot remove current armour");
        }

        //Removing from inventory if it is the same armour instance as the one to remove
        boolean present = armourSet.removeIf(e -> e == armour);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }

    @Override
    public void removePotion(Potion potion) throws InventoryException {
        //Removing from inventory if it is the same potion instance as the one to remove
        boolean present = potionSet.removeIf(e -> e == potion);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }

    //Methods for using observers
    public void addNameChangeObserver(NameChangeObserver observer) {
        nameChangeObservers.add(observer);
    }

    public void addWeaponChangeObserver(WeaponChangeObserver observer) {
        weaponChangeObservers.add(observer);
    }

    public void addArmourChangeObserver(ArmourChangeObserver observer) {
        armourChangeObservers.add(observer);
    }

    public void addGoldChangeObserver(GoldChangeObserver observer) {
        goldChangeObservers.add(observer);
    }

    public void removeNameChangeObserver(NameChangeObserver observer) {
        nameChangeObservers.remove(observer);
    }

    public void removeWeaponChangeObserver(WeaponChangeObserver observer) {
        weaponChangeObservers.remove(observer);
    }

    public void removeArmourChangeObserver(ArmourChangeObserver observer) {
        armourChangeObservers.remove(observer);
    }

    public void removeGoldChangeObserver(GoldChangeObserver observer) {
        goldChangeObservers.remove(observer);
    }

    public void notifyNameChangeObservers(String newName) {
        for (NameChangeObserver observer : nameChangeObservers) {
            observer.notify(newName);
        }
    }

    public void notifyWeaponChangeObservers(Weapon newWeapon) {
        for (WeaponChangeObserver observer : weaponChangeObservers) {
            observer.notify(newWeapon);
        }
    }

    public void notifyArmourChangeObservers(Armour newArmour) {
        for (ArmourChangeObserver observer : armourChangeObservers) {
            observer.notify(newArmour);
        }
    }

    public void notifyGoldChangeObservers(int newGold) {
        for (GoldChangeObserver observer : goldChangeObservers) {
            observer.notify(newGold);
        }
    }
}
