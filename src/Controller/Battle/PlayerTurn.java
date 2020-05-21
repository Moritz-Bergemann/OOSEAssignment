package Controller.Battle;

import Controller.Battle.BattleManager;
import Controller.Battle.BattleState;
import Controller.Battle.EnemyTurn;

public class PlayerTurn extends BattleState {
    public PlayerTurn(BattleManager battle) {
        super(battle);
    }

    @Override
    public void runTurn() {
        battle.getMenu().setStateInfo("Player Turn");
    }

    @Override
    public void endTurn() {
        //Change to enemy turn
        battle.setState(new EnemyTurn(battle));
        battle.continueBattle();
    }
}
