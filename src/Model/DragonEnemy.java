package Model;

import Controller.Chance;

/**
 * Class for dragon enemy type.
 */
public class DragonEnemy extends Enemy {
    public DragonEnemy() {
        super();

        //Initialising dragon combat attributes
        health = 100;
        minAttack = 15;
        maxAttack = 30;
        minDefense = 15;
        maxDefense = 20;
        gold = 100;
    }

    /**
     * @return Enemy name
     */
    @Override
    public String getName() {
        return "Dragon";
    }

    /**
     * Attack including dragon's special abilities
     * @return attack damage
     */
    @Override
    public int calcAttack() {
        //Getting base attack
        int attack = super.calcAttack();

        //Adding 25% chance for attack damage to double OR 10% chance for recovery of 10 health
        if (Chance.chance(0.35)) { //Chance for any ability at all
            if (Chance.chance(0.25/0.35)) { //25% out of 35% chance
                attack *= 2;
            }
            else { //Remaining 10%/35% chance
                this.gainHealth(10);
            }
        }

        return attack;
    }
}
