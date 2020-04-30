package Model;


import Model.Items.Armour;
import Model.Items.InventoryException;
import Model.Items.Potion;
import Model.Items.Weapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player extends Character implements ItemUser {
    private static final int startingHealth = 30; //Starting health of player
    private static final int startingGold = 100; //Starting gold of player
    private static final int startingItemCapacity = 15; //Starting item capacity of player

    private String name;
    private final Set<Weapon> weaponSet;
    private final Set<Armour> armourSet;
    private final Set<Potion> potionSet;

    int itemCapacity;
    private Weapon weapon;
    private Armour armour;
    private int gold;

    public Player() {
        super(startingHealth);
        weaponSet = new HashSet<>();
        armourSet = new HashSet<>();
        potionSet = new HashSet<>();
        itemCapacity = startingItemCapacity;
        weapon = null;
        armour = null;
        gold = startingGold;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumItems() {
        return weaponSet.size() + armourSet.size() + potionSet.size();
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int addedGold) {
        this.gold += addedGold;
    }

    public void loseGold(int lostGold) {
        if (lostGold > this.gold) {
            throw new IllegalArgumentException(String.format("Cannot lose %d gold (only has %d)",
                    lostGold, this.gold)); //FIXME should this be a different exception?
        }
    }

    //Inherited from 'Character'
    @Override
    public int calcAttack() {
        return weapon.calcAttack();
    }

    @Override
    public int calcDefence() {
        return armour.calcDefence();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMinAttack() {
        return weapon.getMinEffect();
    }

    @Override
    public int getMaxAttack() {
        return weapon.getMaxEffect();
    }

    @Override
    public int getMinDefence() {
        return armour.getMinEffect();
    }

    @Override
    public int getMaxDefence() {
        return armour.getMaxEffect();
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
    public void removeWeapon(Weapon weapon) throws InventoryException {
        boolean present = weaponSet.remove(weapon);

        if (!present) {
            throw new IllegalArgumentException("Item not present!");
        }
    }

    @Override
    public void removeArmour(Armour armour) throws InventoryException {
        boolean present = armourSet.remove(armour);

        if (!present) {
            throw new IllegalArgumentException("Item not present!");
        }
    }

    @Override
    public void removePotion(Potion potion) throws InventoryException {
        boolean present = potionSet.remove(potion);

        if (!present) {
            throw new IllegalArgumentException("Item not present!");
        }
    }
}
