package game.model.observers;

import game.controller.RemovableObserver;
import game.model.items.Weapon;

/**
 * Observer for change in player's equipped weapon
 */
public interface WeaponChangeObserver extends RemovableObserver {
    public void notify(Weapon newWeapon);
}
