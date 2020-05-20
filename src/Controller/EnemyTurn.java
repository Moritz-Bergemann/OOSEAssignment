package Controller;

import java.util.concurrent.TimeUnit;

public class EnemyTurn extends BattleState {
    public EnemyTurn(BattleManager battle) {
        super(battle);
    }

    @Override
    public void runTurn() {
        battle.getMenu().setStateInfo("Enemy Turn"); //FIXME this doesn't work properly

        //Making enemy attack player
        battle.runAttack(battle.getEnemy(), battle.getPlayer());

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch (InterruptedException interrupt) {
            System.out.println("Error - enemy turn sleep interrupted"); //TODO add debugPrint()
        }

        //End the turn (since the enemy cannot do anything else)
        endTurn();
    }

    @Override
    public void endTurn() {
        //Change to player turn
        battle.setState(new PlayerTurn(battle));
        battle.continueBattle();
    }
}
