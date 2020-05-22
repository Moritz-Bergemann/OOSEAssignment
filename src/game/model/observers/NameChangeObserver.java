package game.model.observers;

import game.controller.RemovableObserver;

/**
 * Observer for change in GameCharacter name
 */
public interface NameChangeObserver extends RemovableObserver {
    public void notify(String newName);
}
