package game.model.observers;

import game.controller.RemovableObserver;

/**
 * Observer for enemy's use of an ability
 */
public interface AbilityObserver extends RemovableObserver {
    public void notify(String message);
}
