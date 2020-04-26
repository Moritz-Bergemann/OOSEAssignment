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
    public int calcDamage() {
         
    }
}
