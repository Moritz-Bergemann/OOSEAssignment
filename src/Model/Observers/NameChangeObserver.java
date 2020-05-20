package Model.Observers;

import Controller.RemovableObserver;

/**
 * Observer for change in GameCharacter name
 */
public interface NameChangeObserver extends RemovableObserver {
    public void notify(String newName);
}
