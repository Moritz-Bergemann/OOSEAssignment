package game.model.items;

import game.model.Chance;
import game.model.GameCharacter;

/**
 * Class for potion type that heals characters
 */
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

    public HealingPotion(HealingPotion healingPotion) {
        super(healingPotion);
        this.minHealing = healingPotion.minHealing;
        this.maxHealing = healingPotion.maxHealing;
    }

    private int calcHealing() {
        return Chance.randBetween(minHealing, maxHealing);
    }

    /**
     * Heals the potion user by an amount between the minimum & maximum effect
     * @param user Character that used the potion
     * @param enemy Character user was battling when using the potion
     * @return a description of the event
     */
    @Override
    public String use(GameCharacter user, GameCharacter enemy) {
        int healing = calcHealing();

        int gainedHealth = user.gainHealth(healing);

        return String.format("%s drank %s, causing him to regain %d health!", user.getName(), name, gainedHealth);
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

    @Override
    public Potion clone() {
        return new HealingPotion(this);
    }
}
