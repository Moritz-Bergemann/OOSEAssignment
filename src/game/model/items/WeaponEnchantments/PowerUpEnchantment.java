package game.model.items.WeaponEnchantments;

import game.model.items.Weapon;
import game.model.items.WeaponEnchantment;

/**
 * Weapon enchantment that increases damage by 10%
 */
public class PowerUpEnchantment extends WeaponEnchantment {
    private static int cost = 10;

    public static int getIndividualCost() {
        return cost;
    }

    public static String getIndividualDescripton() {
        return "Increases weapon damage by 10%";
    }

    public PowerUpEnchantment(Weapon next) {
        super(next);
    }

    public PowerUpEnchantment(PowerUpEnchantment enchantment) {
        super(enchantment);
    }

    public int calcAttack() {
        //Returning previous attack with damage increased 10%
        return (int)Math.round(next.calcAttack() * 1.1);
    }

    @Override
    public int getCost() {
        return next.getCost() + cost;
    }

    @Override
    public int getMinEffect() {
        return (int)Math.round(next.getMinEffect() * 1.1);
    }

    @Override
    public int getMaxEffect() {
        return (int)Math.round(next.getMaxEffect() * 1.1);
    }

    @Override
    public String getDescription() {
        return next.getDescription() + "\n\t- Power Up";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PowerUpEnchantment that = (PowerUpEnchantment) o;
        return next.equals(that.next);
    }

    @Override
    public PowerUpEnchantment clone() {
        return new PowerUpEnchantment(this);
    }
}
