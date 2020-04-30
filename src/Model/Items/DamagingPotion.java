package Model.Items;

import Controller.Chance;
import Model.Character;

public class DamagingPotion extends Potion {
    int minDamage;
    int maxDamage;

    public DamagingPotion(String name, int cost, int minDamage, int maxDamage) {
        super(name, cost);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    private int calcHealing() {
        return Chance.randBetween(minDamage, maxDamage);
    }

    /**
     * Application of potion - here causing a certain amount of damage
     * @param character the character to be damaged
     */
    @Override
    public void apply(Character character) {
        character.loseHealth(calcHealing());
    }

    @Override
    public String getDescription() {
        String desc = super.getDescription();

        return desc.replace("_TYPE_", "damage");
    }

    @Override
    public int getMinEffect() {
        return 0;
    }

    @Override
    public int getMaxEffect() {
        return 0;
    }
}
