package Controller.Battle;

import Controller.Battle.BattleManager;

public abstract class BattleState {
    protected BattleManager battle;

    public BattleState(BattleManager battle) {
        this.battle = battle;
    }

    /**
     * Initiates the running of the current turn
     */
    public abstract void runTurn();

    /**
     * Ends the current turn (like changing the turn state to that of a different turn)
     */
    public abstract void endTurn();
}
