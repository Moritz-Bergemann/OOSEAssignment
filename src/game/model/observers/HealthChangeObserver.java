package game.model.observers;

import game.controller.RemovableObserver;

/**
 * Observer for change in GameCharacter health
 */
public interface HealthChangeObserver extends RemovableObserver {
    public void notify(int newHealth);
}
