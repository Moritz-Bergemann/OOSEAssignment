package game.controller;

import game.controller.shop.ShopException;
import game.controller.shop.ShopManager;
import game.model.items.Armour;
import game.model.items.Weapon;
import game.model.Player;
import game.view.IntermediateMenu;
import game.view.MenuUtils;

public class IntermediateManager {
    private Player player; //Player playing the game
    private ShopManager shopManager; //Shop manager to take control in case player chooses to visit shop
    private IntermediateMenu menu;

    private boolean quitGame; //Tracks whether the player has chosen to quit the game

    public IntermediateManager(Player player, ShopManager shopManager, IntermediateMenu menu) {
        this.player = player;
        this.shopManager = shopManager;
        this.menu = menu;
        quitGame = false;
    }

    /**
     * Runs the intermediate menu between battles and before the first battle.
     * @return whether the player choose to quit the game or not
     */
    public boolean runIntermediate() {
        //Adding this manager to menu so the menu can call its methods
        menu.setManager(this);

        //Continue displaying main menu until player exits the menu
        menu.showMenu();

        return quitGame;
    }

    //Responses to intermediate menu options
    public void goToShop() {
        try {
            shopManager.runShop();
        }
        catch (ShopException s) {
            MenuUtils.showError("Shop failed", "Encountered error running shop - " + s.getMessage(), null);
        }
    }

    public void chooseCharacterName(String name) {
        player.setName(name);
    }

    public void chooseWeapon(Weapon weapon) {
        player.setCurWeapon(weapon);
    }

    public void chooseArmour(Armour armour) {
        player.setCurArmour(armour);
    }

    public void startBattle() {
        if (player.readyForBattle()) {
            menu.startBattle();
        }
        else {
            menu.showNotReadyForBattle();
        }
    }

    public void exitGame() {
        quitGame = true;
    }
}
