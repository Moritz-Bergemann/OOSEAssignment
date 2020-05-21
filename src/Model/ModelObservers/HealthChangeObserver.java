package Model.ModelObservers;

import Controller.RemovableObserver;

/**
 * Observer for change in GameCharacter health
 */
public interface HealthChangeObserver extends RemovableObserver {
    public void notify(int newHealth);
}
