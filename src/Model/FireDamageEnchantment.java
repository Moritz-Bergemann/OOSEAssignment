package Model;

import Controller.Chance;

public class FireDamageEnchantment extends WeaponEnchantment {
    private static int cost = 20;

    public static int getIndividualCost() {
        return cost;
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
}
