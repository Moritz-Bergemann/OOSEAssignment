package Controller;

import Model.GameCharacter;
import Model.Items.InventoryException;
import Model.Items.Potion;
import Model.Player;

public class BattleManager {
    private Player player;
    private GameCharacter enemy;

    public BattleManager(Player player, GameCharacter enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /**
     * Begins the running of a battle in the game menu
     * @return whether the battle has killed the player TODO change this?
     */
    public boolean runBattle() {
        //TODO create listeners for enemy that can then be added here

        BattleMenu.mainMenu();

        while (player.isAlive() && enemy.isAlive()) {
            playerTurn();

            if (enemy.isAlive()) {
                enemyTurn();
            }
        }

        return player.isAlive();
    }

    private void enemyTurn() {
        //Making player attack enemy (could be updated for further functionality in the future)
        runAttack(enemy, player);
    }

    private void playerTurn() {
        //TODO observers here
        BattleMenu.playerMenu();
    }

    public void runAttack(GameCharacter attacker, GameCharacter defender) {
        //Running calculations to determine damage to deal
        int attackerDamage = attacker.calcAttack();
        int defenderDefence = defender.calcDefence();

        //Calculating damage done as attacker's attack take defender's defense (with minimum damage 0)
        int damageDone = Math.max(attackerDamage - defenderDefence, 0);

        //Dealing damage to defender
        defender.loseHealth(damageDone);

        BattleMenu.showMessage(String.format("%s attacked %s, dealing %d damage! (% d attack vs %d defence)",
                attacker.getName(), defender.getName(), damageDone, attackerDamage, defenderDefence)); //TODO refactor, should probably just pass values to view
    }

    public void usePotion(Potion potion, GameCharacter target) {
        //Removing potion from player's inventory
        try {
            potion.removeFromInventory(player);
        }
        catch (InventoryException inv) {
            throw new IllegalArgumentException("Tried to use potion not in inventory", inv);
        }

        //TODO create listeners for potions that can then be added here

        potion.apply(target);

    }
}
