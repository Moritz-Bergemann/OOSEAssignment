package Model.Observers;

import Controller.RemovableObserver;
import Model.Items.Armour;

/**
 * Observer for change in gameCharacter's equipped weapon
 */
public interface ArmourChangeObserver extends RemovableObserver {
    public void notify(Armour newArmour);
}
