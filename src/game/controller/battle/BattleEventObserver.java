package game.controller.battle;

import game.controller.RemovableObserver;

/**
 * Observer for events that occur during a battle
 */
public interface BattleEventObserver extends RemovableObserver {
    public void notify(String message);
}
