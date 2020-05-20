package Model.Observers;

import Controller.RemovableObserver;

/**
 * Observer for enemy's use of an ability
 */
public interface AbilityObserver extends RemovableObserver {
    public void notify(String message);
}
