package game.model.enemies;

import game.model.Chance;

/**
 * Class for goblin enemy type.
 */
public class GoblinEnemy extends Enemy {
    //Enemy type attributes
    private static final int typeHealth = 30;
    private static final int typeMinAttack = 3;
    private static final int typeMaxAttack = 8;
    private static final int typeMinDefence = 4;
    private static final int typeMaxDefence = 8;
    private static final int typeGold = 20;

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
