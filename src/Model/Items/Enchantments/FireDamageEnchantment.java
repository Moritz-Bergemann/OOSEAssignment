package Model.Items.Enchantments;

import Controller.Chance;
import Model.Items.Weapon;
import Model.Items.WeaponEnchantment;

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
}
