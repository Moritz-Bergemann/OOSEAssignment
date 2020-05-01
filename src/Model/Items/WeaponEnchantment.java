package Model.Items;

import Model.ItemUser;
import Model.Items.InventoryException;
import Model.Items.Weapon;

import java.util.Objects;

public abstract class WeaponEnchantment implements Weapon {
    protected Weapon next;

    public WeaponEnchantment(Weapon next) {
        this.next = next;
    }

    //Standard implementations for weapon properties (may be overridden depending on the enchantment)
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

    @Override
    public void addToInventory(ItemUser itemUser) throws InventoryException {
        next.addToInventory(itemUser);
    }

    @Override
    public void removeFromInventory(ItemUser itemUser) throws InventoryException {
        next.removeFromInventory(itemUser);
    }

    @Override
    public abstract boolean equals(Object o);
}
