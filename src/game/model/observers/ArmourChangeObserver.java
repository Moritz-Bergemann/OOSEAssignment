package game.model.observers;

import game.controller.RemovableObserver;
import game.model.items.Armour;

/**
 * Observer for change in gameCharacter's equipped weapon
 */
public interface ArmourChangeObserver extends RemovableObserver {
    public void notify(Armour newArmour);
}
