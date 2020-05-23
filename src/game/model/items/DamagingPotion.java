package game.model.items;

import game.model.Chance;
import game.model.GameCharacter;

/**
 * Class for potion type that damages characters
 */
public class DamagingPotion extends Potion {
    int minDamage;
    int maxDamage;

    public DamagingPotion(String name, int cost, int minDamage, int maxDamage) {
        super(name, cost);
        
        if (this.minDamage < 0 || this.maxDamage < 0) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (this.minDamage > this.maxDamage) {
            throw new IllegalArgumentException("Minimum damage cannot be greater than maximum");
        }
        
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public DamagingPotion(DamagingPotion damagingPotion) {
        super(damagingPotion);
        this.minDamage = damagingPotion.minDamage;
        this.maxDamage = damagingPotion.maxDamage;
    }

    private int calcDamage() {
        return Chance.randBetween(minDamage, maxDamage);
    }

    /**
     * Application of potion - here causing a certain amount of damage
     * @param gameCharacter the character to be damaged
     * @return damage done
     */
    @Override
    public int apply(GameCharacter gameCharacter) {
        int damage = calcDamage();

        gameCharacter.loseHealth(damage);

        return damage;
    }

    @Override
    public String use(GameCharacter user, GameCharacter enemy) {
        int damage = calcDamage();

        enemy.loseHealth(damage);

        return (String.format("%s throws a %s at %s, dealing %d damage!", user.getName(), name, enemy.getName(),
                damage));
    }

    @Override
    public String getEffectType() {
        return "damage";
    }

    @Override
    public String getDescription() {
        String desc = super.getDescription();

        return desc.replace("_TYPE_", "damage");
    }

    @Override
    public int getMinEffect() {
        return minDamage;
    }

    @Override
    public int getMaxEffect() {
        return maxDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DamagingPotion that = (DamagingPotion) o;
        return minDamage == that.minDamage &&
                maxDamage == that.maxDamage;
    }

    @Override
    public Potion clone() {
        return new DamagingPotion(this);
    }
}
