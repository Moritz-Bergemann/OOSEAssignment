package Controller.Battle;

import Controller.RemovableObserver;

/**
 * Observer for events that occur during a battle
 */
public interface BattleEventObserver extends RemovableObserver {
    public void notify(String message);
}
