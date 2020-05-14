package Model.Items.Enchantments;

import Model.Items.Weapon;
import Model.Items.WeaponEnchantment;

public class DamageUp2Enchantment extends WeaponEnchantment {
    private static int cost = 5;

    public static int getIndividualCost() {
        return cost;
    }

    public static String getIndividualDescripton() {
        return "Increases weapon damage by 2";
    }

    public DamageUp2Enchantment(Weapon next) {
        super(next);
    }

    public int calcAttack() {
        //Returning previous attack with added 2 damage
        return next.calcAttack() + 2;
    }

    @Override
    public int getCost() {
        return next.getCost() + cost;
    }

    @Override
    public int getMinEffect() {
        return next.getMinEffect() + 2;
    }

    @Override
    public int getMaxEffect() {
        return next.getMaxEffect() + 2;
    }

    @Override
    public String getDescription() {
        return next.getDescription() + "\n\t- Damage up 2";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DamageUp2Enchantment that = (DamageUp2Enchantment) o;
        return next.equals(that.next);
    }
}
