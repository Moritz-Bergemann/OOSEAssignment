package Model;

public class DamageUp2Enchantment extends WeaponEnchantment {
    private static int cost = 5;

    public static int getIndividualCost() {
        return cost;
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
}
