package Model.ModelObservers;

import Controller.RemovableObserver;

/**
 * Observer for change in character gold
 */
public interface GoldChangeObserver extends RemovableObserver {
    public void notify(int newGoldAmount);
}
