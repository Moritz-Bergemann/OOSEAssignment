package Model.Items;

import Controller.Chance;
import Model.Character;

public class HealingPotion extends Potion {
    int minHealing;
    int maxHealing;

    public HealingPotion(String name, int cost, int minHealing, int maxHealing) {
        super(name, cost);
        this.minHealing = minHealing;
        this.maxHealing = maxHealing;
    }

    private int calcHealing() {
        return Chance.randBetween(minHealing, maxHealing);
    }

    /**
     * Application of potion - here causing a certain amount of healing
     * @param character the character to be healed
     */
    @Override
    public void apply(Character character) {
        character.gainHealth(calcHealing());
    }

    @Override
    public int getMinEffect() {
        return 0;
    }

    @Override
    public int getMaxEffect() {
        return 0;
    }

    @Override
    public String getDescription() {
        String desc = super.getDescription();

        return desc.replace("_TYPE_", "damage");
    }
}
