package Model;


import Model.Items.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player extends GameCharacter implements ItemUser {
    private static final int startingHealth = 30; //Starting health of player
    private static final int startingGold = 100; //Starting gold of player
    private static final int startingItemCapacity = 15; //Starting item capacity of player

    private String name;
    private final Set<Weapon> weaponSet;
    private final Set<Armour> armourSet;
    private final Set<Potion> potionSet;
    int itemCapacity;
    private Weapon curWeapon;
    private Armour curArmour;
    private int gold;

    private boolean wonGame;

    /**
     * Default constructor
     * NOTE: name, weapon and armour variables should be initialised before character can attempt a battle
     */
    public Player() {
        super(startingHealth);
        name = null;
        weaponSet = new HashSet<>();
        armourSet = new HashSet<>();
        potionSet = new HashSet<>();
        itemCapacity = startingItemCapacity;
        curWeapon = null;
        curArmour = null;
        gold = startingGold;
        wonGame = false;
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
    }

    /**
     * Sets the characters weapon
     * @param armour armour for character to equip
     */
    public void setCurArmour(Armour armour) {
        this.curArmour = armour;
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
        this.gold += addedGold;
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
    public Set<Weapon> getWeaponSet() {
        return Collections.unmodifiableSet(weaponSet);
    }

    @Override
    public Set<Armour> getArmourSet() {
        return Collections.unmodifiableSet(armourSet);
    }

    @Override
    public Set<Potion> getPotionSet() {
        return Collections.unmodifiableSet(potionSet);
    }

    @Override
    public Set<Item> getItemSet() {
        Set<Item> allItems = new HashSet<>();

        allItems.addAll(weaponSet);
        allItems.addAll(armourSet);
        allItems.addAll(potionSet);

        return Collections.unmodifiableSet(allItems);
    }

    @Override
    public void removeWeapon(Weapon weapon) throws InventoryException {
        if (weapon.equals(curWeapon)) {
            throw new InventoryException("Cannot remove current weapon");
        }

        boolean present = weaponSet.remove(weapon);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }

    @Override
    public void removeArmour(Armour armour) throws InventoryException {
        if (armour.equals(curArmour)) {
            throw new InventoryException("Cannot remove current armour");
        }

        boolean present = armourSet.remove(armour);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }

    @Override
    public void removePotion(Potion potion) throws InventoryException {
        boolean present = potionSet.remove(potion);

        if (!present) {
            throw new IllegalArgumentException("Item not present");
        }
    }
}
