package Model;


import java.util.HashSet;

public class Player extends Character {
    private static int startingHealth = 30; //Starting health of player
    private static int startingGold = 100; //Starting gold of player

    private String name;
    private Set<Item> inventory;
    private Weapon weapon;
    private Armour armour;
    private int gold;

    public Player() {
        super(startingHealth);
        inventory = new HashSet<>();
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
        return weapon.getMinAttack();
    }

    @Override
    public int getMaxAttack() {
        return weapon.getMaxAttack();
    }

    @Override
    public int getMinDefense() {
        return armour.getMinDefense();
    }

    @Override
    public int getMaxDefense() {
        return armour.getMaxDefense();
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


}
