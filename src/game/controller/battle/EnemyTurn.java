package game.controller.battle;

import game.view.MenuUtils;

import java.util.concurrent.TimeUnit;

public class EnemyTurn extends BattleState {
    public EnemyTurn(BattleManager battle) {
        super(battle);
    }

    //NOTE: There's a slight issue here in the presentation - for whatever reason, the wait before the enemy turn
    //  ends always occurs BEFORE the user interface is updated, even if the wait is run AFTER the command to
    //  update the code (as it is here). I've tried using Platform.runLater() and multiple other thread-related
    //  methods, but nothing fixes it. It just makes the turn timing look a bit off and isn't really part of the
    //  assignment requirements, but I'd appreciate if anyone knows what is causing this.
    @Override
    public void runTurn() {
        battle.getMenu().setStateInfo("Enemy Turn");

        //Making enemy attack player
        battle.runAttack(battle.getEnemy(), battle.getPlayer());

        //Waiting (to allow user to read what occurs on screen)
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch (InterruptedException interrupt) {
            MenuUtils.logError("Enemy turn sleep interrupted");
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
