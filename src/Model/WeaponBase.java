package Model;
import Controller.Chance;

public class WeaponBase implements Weapon {
    String name;
    int cost;
    String weaponType;
    String damageType;
    int minAttack;
    int maxAttack;

    public WeaponBase(String name, String weaponType, String damageType, int minAttack, int maxAttack, int cost) {
        this.name = name;
        this.weaponType = weaponType;
        this.damageType = damageType;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
    }

    @Override
    public int calcAttack() {
        return Chance.randBetween(minAttack, maxAttack);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getMinEffect() {
        return minAttack;
    }

    @Override
    public int getMaxEffect() {
        return maxAttack;
    }
}
