package Model.Items;

public class DamageUp5Enchantment extends WeaponEnchantment {
    private static int cost = 10;

    public static int getIndividualCost() {
        return cost;
    }

    public DamageUp5Enchantment(Weapon next) {
        super(next);
    }

    public int calcAttack() {
        //Returning previous attack with added 5 damage
        return next.calcAttack() + 5;
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
        return next.getMaxEffect() + 5;
    }

    @Override
    public String getDescription() {
        return next.getDescription() + "\n\t- Damage up 5";
    }
}
