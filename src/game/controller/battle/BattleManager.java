package game.controller.battle;

import game.controller.RemovableObserver;
import game.model.enemies.DragonEnemy;
import game.model.GameCharacter;
import game.model.items.InventoryException;
import game.model.items.Potion;
import game.model.observers.AbilityObserver;
import game.model.Player;
import game.view.BattleMenu;

import java.util.LinkedList;
import java.util.List;

/**
 * (Pseudo) Event-driven controller for battles that occur.
 * Creates the battle user interface and waits for the view to call its methods to perform operations during the battle.
 */
public class BattleManager {
    private Player player;
    private GameCharacter enemy;
    private BattleMenu battleMenu;
    private BattleState state;

    private List<BattleEventObserver> battleEventObservers;
    private List<RemovableObserver> observers;

    public BattleManager(Player player, GameCharacter enemy, BattleMenu battleMenu) {
        this.player = player;
        this.enemy = enemy;
        this.battleMenu = battleMenu;

        battleEventObservers = new LinkedList<>();
        observers = new LinkedList<>();
        state = new PlayerTurn(this); //Setting default state to avoid null value
    }

    /**
     * Begins the running of a battle in the game menu
     * @return whether the battle has killed the player
     */
    public boolean runBattle() {
        //Setting initial turn to player turn
        state = new PlayerTurn(this);

        battleMenu.setManager(this);

        //Adding observer for enemy abilities that notifies the battle menu in the form of a battle event
        AbilityObserver abilityObs = new AbilityObserver() {
            @Override
            public void notify(String message) {
                notifyBattleEventObservers(message);
            }

            @Override
            public void removeSelf() {
                enemy.removeAbilityObserver(this);
            }
        };
        enemy.addAbilityObserver(abilityObs);
        observers.add(abilityObs);

        battleMenu.showMenu();

        //Making all created observers remove themselves
        for (RemovableObserver observer : observers) {
            observer.removeSelf();
        }

        //Making player win if killed dragon //FIXME instanceof bad
        if (!enemy.isAlive() && enemy instanceof DragonEnemy) {
            player.setWonGame();
        }

        return !player.isAlive();
    }

    /**
     * Run an attack between two game characters, using their respective attack and defense
     * @param attacker attacking character
     * @param defender defending character
     */
    public void runAttack(GameCharacter attacker, GameCharacter defender) {
        //Running calculations to determine damage to deal
        int attackerDamage = attacker.calcAttack();
        int defenderDefence = defender.calcDefence();

        //Calculating damage done as attacker's attack take defender's defense (with minimum damage 0)
        int damageDone = Math.max(attackerDamage - defenderDefence, 0);

        //Dealing damage to defender
        defender.loseHealth(damageDone);

        notifyBattleEventObservers(String.format("%s attacked %s, dealing %d damage! (% d attack vs %d defence)",
                attacker.getName(), defender.getName(), damageDone, attackerDamage, defenderDefence));
    }

    /**
     * Applies the potion's effect to the targeted character
     * @param potion potion to use
     * @param target character to use the potion on
     */
    public void usePotion(Potion potion, GameCharacter target) {
        //Removing potion from player's inventory
        try {
            potion.removeFromInventory(player);
        }
        catch (InventoryException inv) {
            throw new IllegalArgumentException("Tried to use potion not in inventory", inv);
        }

        int effect = potion.apply(target);

        notifyBattleEventObservers(String.format("%s used on %s, causing %d %s", potion.getName(), target.getName(),
                effect, potion.getEffectType()));
    }


    //Methods for interaction with state:
    /**
     * Gets the battle's menu (so the state can send it information)
     * @return The battle manager's menu
     */
    public BattleMenu getMenu() {
        return battleMenu;
    }

    /**
     * Sets the state of the battle manager
     * @param state the state to set the battle to
     */
    public void setState(BattleState state) {
        this.state = state;
    }

    public GameCharacter getEnemy() {
        return enemy;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Ends the current turn (likely causing move to next turn)
     */
    public void endTurn() {
        state.endTurn();
    }

    /**
     * Checks whether the player or enemy have died and ends the battle if this is the case. If not, the next turn of
     *  the battle is run.
     */
    public void continueBattle() {
        if (!player.isAlive()) { //If player has died
            battleMenu.showBattleEnded(String.format("%s has been defeated!", player.getName()));
        }
        else if (!enemy.isAlive()) { //If enemy has died
            player.gainGold(enemy.getGold());
            battleMenu.showBattleEnded(String.format("%s has been defeated! %d gold earned.",
                    enemy.getName(), enemy.getGold()));
        }
        else { //If battle is still ongoing
            state.runTurn(); //Continue battle
        }
    }

    //Methods for observers
    public void notifyBattleEventObservers(String message) {
        for (BattleEventObserver listener : battleEventObservers) {
            listener.notify(message);
        }
    }

    /**
     * Adds battle event listener
     * @param listener listener to be added
     */
    public void addBattleEventObserver(BattleEventObserver listener) {
        battleEventObservers.add(listener);
    }

    /**
     * Removes battle event listener
     * @param listener listener to be removed
     */
    public void removeBattleEventObserver(BattleEventObserver listener) {
        battleEventObservers.remove(listener);
    }}
