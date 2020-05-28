package game.model.enemies;

import game.model.Chance;

/**
 * Class for ogre enemy type.
 */
public class OgreEnemy extends Enemy {
    //Enemy type attributes
    private static final int typeHealth = 40;
    private static final int typeMinAttack = 5;
    private static final int typeMaxAttack = 10;
    private static final int typeMinDefence = 6;
    private static final int typeMaxDefence = 12;
    private static final int typeGold = 40;

    public OgreEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefence, typeMaxDefence, typeGold);
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
            notifyAbilityObservers("The ogre uses its strength to attack twice in one movement!");
        }

        return attack;
    }
}
