package game.model.items.WeaponEnchantments;

import game.model.Chance;
import game.model.items.Weapon;
import game.model.items.WeaponEnchantment;

/**
 * Weapon enchantment that increases damage by between 5-10 (randomly)
 */
public class FireDamageEnchantment extends WeaponEnchantment {
    private static int cost = 20;

    public static int getIndividualCost() {
        return cost;
    }

    public static String getIndividualDescripton() {
        return "Increases weapon damage by 5-10 (randomly)";
    }

    public FireDamageEnchantment(Weapon next) {
        super(next);
    }

    public FireDamageEnchantment(FireDamageEnchantment enchantment) {
        super(enchantment);
    }


    public int calcAttack() {
        //Returning previous attack with added random 5-10 damage
        return next.calcAttack() + Chance.randBetween(5, 10);
    }

    @Override
    public int getCost() {
        return next.getCost() + cost;
    }

    @Override
    public int getMinEffect() {
        return next.getMinEffect() + 5;
    }

    @Override
    public int getMaxEffect() {
        return next.getMaxEffect() + 10;
    }

    @Override
    public String getDescription() {
        return next.getDescription() + "\n\t- Fire Damage";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FireDamageEnchantment that = (FireDamageEnchantment) o;
        return next.equals(that.next);
    }

    @Override
    public FireDamageEnchantment clone() {
        return new FireDamageEnchantment(this);
    }
}
