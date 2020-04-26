package Model;

import Controller.Chance;

/**
 * Class for goblin enemy type.
 */
public class GoblinEnemy extends Enemy {
    public GoblinEnemy() {
        super();

        //Initialising goblin combat attributes
        health = 30;
        minAttack = 3;
        maxAttack = 8;
        minDefense = 4;
        maxDefense = 8;
        gold = 20;
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
