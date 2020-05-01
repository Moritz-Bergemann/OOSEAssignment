package Model.Items;

import Controller.Chance;
import Model.GameCharacter;

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

    private int calcHealing() {
        return Chance.randBetween(minDamage, maxDamage);
    }

    /**
     * Application of potion - here causing a certain amount of damage
     * @param gameCharacter the character to be damaged
     */
    @Override
    public void apply(GameCharacter gameCharacter) {
        gameCharacter.loseHealth(calcHealing());
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
}
