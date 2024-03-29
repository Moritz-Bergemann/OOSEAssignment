package game.model.enemies;

import game.model.Chance;

/**
 * Class for dragon enemy type.
 */
public class DragonEnemy extends Enemy {
    //Enemy type attributes
    private static final int typeHealth = 100;
    private static final int typeMinAttack = 15;
    private static final int typeMaxAttack = 30;
    private static final int typeMinDefence = 15;
    private static final int typeMaxDefence = 20;
    private static final int typeGold = 100;

    public DragonEnemy() {
        super(typeHealth, typeMinAttack, typeMaxAttack, typeMinDefence, typeMaxDefence, typeGold);
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
                notifyAbilityObservers("The dragon uses it's arcane powers to double its damage!");
            }
            else { //Remaining 10%/35% chance
                this.gainHealth(10);
                notifyAbilityObservers("The dragon's immortal energy causes it to regain 10 health!");
            }
        }

        return attack;
    }
}
