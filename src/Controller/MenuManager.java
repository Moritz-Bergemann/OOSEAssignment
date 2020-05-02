package Controller;

import Model.Items.Armour;
import Model.Items.Weapon;
import Model.Player;

public class MenuManager {
    private Player player; //Player playing the game
    private ShopManager shopManager; //Shop manager to take control in case player chooses to visit shop

    private boolean exitedMenu; //Tracks whether the menu should be exited to continue with the game control flow
    private boolean quitGame; //Tracks whether the player has chosen to quit the game

    public MenuManager(Player player, ShopManager shopManager) {
        this.player = player;
        this.shopManager = shopManager;
        exitedMenu = false;
        quitGame = false;
    }

    public void intermediateMenu() {
        exitedMenu = false;

        //TODO create listeners

        //Continue displaying main menu until player exits the menu
        while (!exitedMenu) {
            MainMenu.showMenu();
        }

        return
    }

    public void gameOverMenu() {
        if (!player.isAlive()) {
            MainMenu.showDiedMenu(player);
        }
        else if (player.wonGame()) {
            MainMenu.showWonMenu(player);
        }
    }

    //Responses to intermediate menu options
    private void goToShop() {
        shopManager.runShop(player);
    }

    private void chooseCharacterName(String name) {
        player.setName(name);
    }

    private void chooseWeapon(Weapon weapon) {
        player.setCurWeapon(weapon);
    }

    private void chooseArmour(Armour armour) {
        player.setCurArmour(armour);
    }

    private void startBattle() {
        if (player.readyForBattle()) {
            MainMenu.showMessage("Starting the next battle...");
            exitedMenu = true;
        }
        else {
            MainMenu.showNotReadyForBattle();
        }
    }

    private void exitGame() {

    }
}
