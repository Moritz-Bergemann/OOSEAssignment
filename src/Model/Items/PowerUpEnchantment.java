package Model.Items;

public class PowerUpEnchantment extends WeaponEnchantment {
    private static int cost = 10;

    public static int getIndividualCost() {
        return cost;
    }

    public PowerUpEnchantment(Weapon next) {
        super(next);
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
}
