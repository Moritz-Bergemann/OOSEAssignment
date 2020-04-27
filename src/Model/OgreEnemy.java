package Model;

import Controller.Chance;

/**
 * Class for ogre enemy type.
 */
public class OgreEnemy extends Enemy {
    //Enemy type attributes
    private static int typeHealth = 40;
    private static int typeMinAttack = 5;
    private static int typeMaxAttack = 10;
    private static int typeMinDefense = 6;
    private static int typeMaxDefense = 12;
    private static int typeGold = 40;

    public OgreEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefense, typeMaxDefense, typeGold);
    }

    /**
     * @return Enemy name
     */
    @Override
    public String getName() {
        return "Ogre";
    }

    /**
     * Attack including ogre's special ability
     * @return attack damage
     */
    @Override
    public int calcAttack() {
        //Getting base attack
        int attack = super.calcAttack();

        //Adding 20% chance for attack to happen twice
        if (Chance.chance(0.5)) {
            attack += super.calcAttack();
        }

        return attack;
    }
}
