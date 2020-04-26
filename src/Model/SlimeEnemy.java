package Model;

import Controller.Chance;

/**
 * Class for Slime enemy type.
 */
public class SlimeEnemy extends Enemy {
    public SlimeEnemy() {
        super();

        //Initialising slime combat attributes
        health = 10;
        minAttack = 3;
        maxAttack = 5;
        minDefense = 0;
        maxDefense = 2;
        gold = 10;
    }

    /**
     * @return Enemy name
     */
    @Override
    public String getName() {
        return "Slime";
    }

    /**
     * Attack including slime's special ability (or the opposite of an ability in this case)
     * @return attack damage
     */
    @Override
    public int calcAttack() {
        //Getting base attack
        int attack = super.calcAttack();

        //Adding 20% chance for attack to have no damage
        if (Chance.chance(0.2)) {
            attack = 0;
        }

        return attack;
    }
}
