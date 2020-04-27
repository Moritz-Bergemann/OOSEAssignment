package Model;


import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player extends Character implements HasInventory {
    private static int startingHealth = 30; //Starting health of player
    private static int startingGold = 100; //Starting gold of player
    private static int startingItemCapacity = 15; //Starting item capacity of player

    private String name;
    private Set<Item> inventory;
    int itemCapacity;
    private Weapon weapon;
    private Armour armour;
    private int gold;

    public Player() {
        super(startingHealth);
        inventory = new HashSet<>();
        itemCapacity = startingItemCapacity;
        weapon = null;
        armour = null;
        gold = startingGold;
    }

    @Override
    public int calcAttack() {
        return weapon.calcAttack();
    }

    @Override
    public int calcDefense() {
        return armour.calcDefense();
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
    public int getMinDefense() {
        return armour.getMinEffect();
    }

    @Override
    public int getMaxDefense() {
        return armour.getMaxEffect();
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

    //Methods from HasInventory interface
    @Override
    public Set<Item> getItemSet() {
        return Collections.unmodifiableSet(inventory);
    }

    @Override
    public Set<Weapon> getWeaponSet() {
        Set<Weapon> weaponSet = new HashSet<>();

        for (Item i : inventory) {
            if (i instanceof Weapon) {
                weaponSet.add((Weapon)i);
            }
        }

        return Collections.unmodifiableSet(weaponSet);
    }

    @Override
    public Set<Armour> getArmourSet() {
        Set<Armour> armourSet = new HashSet<>();

        for (Item i : inventory) {
            if (i instanceof Armour) {
                armourSet.add((Armour)i);
            }
        }

        return Collections.unmodifiableSet(armourSet);
    }

    @Override
    public Set<Potion> getPotionSet() {
        Set<Potion> potionSet = new HashSet<>();

        for (Item i : inventory) {
            if (i instanceof Potion) {
                potionSet.add((Potion)i);
            }
        }

        return Collections.unmodifiableSet(potionSet);
    }

    @Override
    public int getNumItems() {
        return inventory.size();
    }

    @Override
    public void addItem(Item item) throws InventoryException {
        if (getNumItems() == itemCapacity) {
            throw new InventoryException("Inventory is full");
        }

        inventory.add(item);
    }

    @Override
    public void removeItem(Item item) throws InventoryException {
        boolean itemRemoved = inventory.remove(item);

        if (!itemRemoved) {
            throw new InventoryException("Item is not in inventory");
        }
    }
}
