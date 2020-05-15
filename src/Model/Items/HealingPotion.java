package Model.Items;

import Controller.Chance;
import Model.GameCharacter;

public class HealingPotion extends Potion {
    int minHealing;
    int maxHealing;

    public HealingPotion(String name, int cost, int minHealing, int maxHealing) {
        super(name, cost);

        if (this.minHealing < 0 || this.maxHealing < 0) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (this.minHealing > this.maxHealing) {
            throw new IllegalArgumentException("Minimum healing cannot be greater than maximum");
        }

        this.minHealing = minHealing;
        this.maxHealing = maxHealing;
    }

    private int calcHealing() {
        return Chance.randBetween(minHealing, maxHealing);
    }

    /**
     * Application of potion - here causing a certain amount of healing
     * @param gameCharacter the character to be healed
     * @return healing done
     */
    @Override
    public int apply(GameCharacter gameCharacter) {
        int healing = calcHealing();

        gameCharacter.gainHealth(healing);

        return healing;
    }

    @Override
    public String getEffectType() {
        return "healing";
    }

    @Override
    public int getMinEffect() {
        return minHealing;
    }

    @Override
    public int getMaxEffect() {
        return maxHealing;
    }

    @Override
    public String getDescription() {
        String desc = super.getDescription();

        return desc.replace("_TYPE_", "healing");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealingPotion that = (HealingPotion) o;
        return minHealing == that.minHealing &&
                maxHealing == that.maxHealing;
    }
}
