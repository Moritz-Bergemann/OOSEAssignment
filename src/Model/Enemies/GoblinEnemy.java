package Model.Enemies;

import Model.Chance;

/**
 * Class for goblin enemy type.
 */
public class GoblinEnemy extends Enemy {
    //Enemy type attributes
    private static int typeHealth = 30;
    private static int typeMinAttack = 3;
    private static int typeMaxAttack = 8;
    private static int typeMinDefence = 4;
    private static int typeMaxDefence = 8;
    private static int typeGold = 20;

    public GoblinEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefence, typeMaxDefence, typeGold);
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
            notifyAbilityObservers("The goblin tries extra hard on this attack, increasing its damage by 3.");
        }

        return attack;
    }
}
