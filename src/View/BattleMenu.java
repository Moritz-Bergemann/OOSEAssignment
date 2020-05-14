package View;

import Controller.GameManager;
import Model.GameCharacter;
import Model.Player;
import javafx.stage.Stage;

public class BattleMenu {
    private Player player;
    private GameCharacter enemy;
    private Stage menuStage;
    private GameManager manager;

    public BattleMenu(Player player, GameCharacter enemy, Stage mainStage) {
        this.player = player;
        this.enemy = enemy;
        this.manager = null;
        menuStage = new Stage();
        menuStage.initOwner(mainStage);
    }

    public void setManager(GameManager manager) {
        this.manager = manager;
    }

    public void playerTurn() {

    }

    public void addEvent(String format) {
    }

    public void showMenu() {
    }
}
