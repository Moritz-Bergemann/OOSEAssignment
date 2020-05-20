package Model.Observers;

import Controller.RemovableObserver;
import Model.Items.Weapon;

/**
 * Observer for change in player's equipped weapon
 */
public interface WeaponChangeObserver extends RemovableObserver {
    public void notify(Weapon newWeapon);
}
