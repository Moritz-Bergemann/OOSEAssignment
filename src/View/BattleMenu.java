package View;

import Model.GameCharacter;
import Model.Player;
import javafx.stage.Stage;

public class BattleMenu {
    private Player player;
    private GameCharacter enemy;
    private Stage menuStage;

    public BattleMenu(Player player, GameCharacter enemy, Stage mainStage) {
        this.player = player;
        this.enemy = enemy;
        menuStage = new Stage();
        menuStage.initOwner(mainStage);
    }

    public void playerTurn() {
    }

    public void addEvent(String format) {
    }

    public void showMenu() {
    }
}
