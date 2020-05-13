package Controller;

import Model.Items.Armour;
import Model.Items.Weapon;
import Model.Player;
import View.IntermediateMenu;

public class IntermediateManager {
    private Player player; //Player playing the game
    private ShopManager shopManager; //Shop manager to take control in case player chooses to visit shop
    private IntermediateMenu menu;

    private boolean exitedMenu; //Tracks whether the menu should be exited to continue with the game control flow
    private boolean quitGame; //Tracks whether the player has chosen to quit the game

    public IntermediateManager(Player player, ShopManager shopManager, IntermediateMenu menu) {
        this.player = player;
        this.shopManager = shopManager;
        this.menu = menu;
        exitedMenu = false;
        quitGame = false;
    }


//    public void gameOverMenu() { //TODO put this somewhere else
//        if (!player.isAlive()) {
//            menu.showDiedMenu(player);
//        }
//        else if (player.wonGame()) {
//            menu.showWonMenu(player);
//        }
//    }


    /**
     * Runs the intermediate menu between battles and before the first battle.
     * @return whether the player choose to quit the game or not
     */
    public boolean intermediateMenu() {
        exitedMenu = false;

        //Adding this manager to menu so the menu can call its methods
        menu.addManager(this);

        //Continue displaying main menu until player exits the menu
        menu.showMenu();

        return quitGame;
    }

    //Responses to intermediate menu options
    public void goToShop() {
        shopManager.runShop(player);
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
            exitedMenu = true;
        }
        else {
            menu.showNotReadyForBattle();
        }
    }

    public void exitGame() {
        quitGame = true;
    }

    public void gameOverMenu() {
    }
}
