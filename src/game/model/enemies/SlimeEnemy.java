package game.model.enemies;

import game.model.Chance;

/**
 * Class for Slime enemy type.
 */
public class SlimeEnemy extends Enemy {
    //Enemy type attributes
    private static final int typeHealth = 10;
    private static final int typeMinAttack = 3;
    private static final int typeMaxAttack = 5;
    private static final int typeMinDefence = 0;
    private static final int typeMaxDefence = 2;
    private static final int typeGold = 10;

    public SlimeEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefence, typeMaxDefence, typeGold);
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
            notifyAbilityObservers("This slime wasn't really feeling it this turn, and does no damage.");
        }

        return attack;
    }
}
