package Model;

public abstract class WeaponEnchantment implements Weapon {
    protected Weapon next;

    public WeaponEnchantment(Weapon next) {
        this.next = next;
    }

    @Override
    public int calcAttack() {
        return next.calcAttack();
    }

    @Override
    public String getName() {
        return next.getName();
    }

    @Override
    public int getCost() {
        return next.getCost();
    }

    @Override
    public int getMinEffect() {
        return next.getMinEffect();
    }

    @Override
    public int getMaxEffect() {
        return next.getMaxEffect();
    }
}
