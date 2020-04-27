package Model;

import Controller.Chance;

/**
 * Class for goblin enemy type.
 */
public class GoblinEnemy extends Enemy {
    //Enemy type attributes
    private static int typeHealth = 30;
    private static int typeMinAttack = 3;
    private static int typeMaxAttack = 8;
    private static int typeMinDefense = 4;
    private static int typeMaxDefense = 8;
    private static int typeGold = 20;

    public GoblinEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefense, typeMaxDefense, typeGold);
    }

    /**
     * @return Enemy name
     */
    @Override
    public String getName() {
        return "Goblin";
    }

    /**
     * Attack including goblin's special ability
     * @return attack damage
     */
    @Override
    public int calcAttack() {
        //Getting base attack
        int attack = super.calcAttack();

        //Adding 50% chance for attack to have 3 extra damage
        if (Chance.chance(0.5)) {
            attack += 3;
        }

        return attack;
    }
}
