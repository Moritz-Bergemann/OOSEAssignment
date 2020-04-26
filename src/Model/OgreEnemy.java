package Model;

import Controller.Chance;

/**
 * Class for ogre enemy type.
 */
public class OgreEnemy extends Enemy {
    public OgreEnemy() {
        super();

        //Initialising ogre combat attributes
        health = 40;
        minAttack = 5;
        maxAttack = 10;
        minDefense = 6;
        maxDefense = 12;
        gold = 40;
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
