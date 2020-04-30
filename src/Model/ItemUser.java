package Model;

import Model.Items.Armour;
import Model.Items.InventoryException;
import Model.Items.Potion;
import Model.Items.Weapon;

import java.util.Set;

/**
 * Interface for entities that use items (and therefore need knowledge of the specific items they possess
 *  so they may be used in the correct context).
 */
public interface ItemUser {
    public void addWeapon(Weapon weapon) throws InventoryException;

    public void addArmour(Armour armour) throws InventoryException;

    public void addPotion(Potion potion) throws InventoryException;

    public Set<Weapon> getWeaponSet();

    public Set<Armour> getArmourSet();

    public Set<Potion> getPotionSet();

    public void removeWeapon(Weapon weapon) throws InventoryException;

    public void removeArmour(Armour armour) throws InventoryException;

    public void removePotion(Potion potion) throws InventoryException;
}
