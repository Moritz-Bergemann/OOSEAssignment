package game.model.items;
import game.model.Chance;
import game.model.ItemUser;

public class WeaponBase implements Weapon {
    String name;
    int cost;
    String weaponType;
    String damageType;
    int minAttack;
    int maxAttack;

    public WeaponBase(String name, String weaponType, String damageType, int minAttack, int maxAttack, int cost) {
        if (name.length() == 0 || weaponType.length() == 0 | damageType.length() == 0) {
            throw new IllegalArgumentException("String cannot be empty");
        }
        if (minAttack < 0 || maxAttack < 0 || cost < 0) {
            throw new IllegalArgumentException("Value cannot be less than 0");
        }
        if (minAttack > maxAttack) {
            throw new IllegalArgumentException("Minimum attack cannot be greater than maximum attack");
        }

        this.name = name;
        this.weaponType = weaponType;
        this.damageType = damageType;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.cost = cost;
    }

    public WeaponBase(WeaponBase weaponBase) {
        this.name = weaponBase.name;
        this.cost = weaponBase.cost;
        this.weaponType = weaponBase.weaponType;
        this.damageType = weaponBase.damageType;
        this.minAttack = weaponBase.minAttack;
        this.maxAttack = weaponBase.maxAttack;
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

    @Override
    public void addToInventory(ItemUser itemUser) throws InventoryException {
        itemUser.addWeapon(this);
    }

    @Override
    public void removeFromInventory(ItemUser itemUser) throws InventoryException {
        itemUser.removeWeapon(this);
    }

    @Override
    public String getDescription() {
        return String.format("%s %s", damageType, weaponType);
    }

    @Override
    public String getType() {
        return "weapon";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeaponBase that = (WeaponBase) o;
        return cost == that.cost &&
                minAttack == that.minAttack &&
                maxAttack == that.maxAttack &&
                name.equals(that.name) &&
                weaponType.equals(that.weaponType) &&
                damageType.equals(that.damageType);
    }

    @Override
    public WeaponBase clone() {
        return new WeaponBase(this);
    }
}
