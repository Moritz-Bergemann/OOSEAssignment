package View;

import Controller.BattleEventListener;
import Controller.BattleManager;
import Model.GameCharacter;
import Model.Items.Potion;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashSet;
import java.util.Set;

public class BattleMenu {
    private Player player;
    private GameCharacter enemy;
    private Stage menuStage;
    private BattleManager manager;

    private Text stateInfo;

    public BattleMenu(Player player, GameCharacter enemy, Stage mainStage) {
        this.player = player;
        this.enemy = enemy;
        this.manager = null;
        menuStage = new Stage();
        menuStage.initOwner(mainStage);
    }

    public void setManager(BattleManager manager) {
        this.manager = manager;
    }

    public void showMenu() {
        menuStage.setTitle("Battle!");

        //Setting up Player & enemy info
        GridPane playerInfoGrid = createInfoGrid(player);
        GridPane enemyInfoGrid = createInfoGrid(enemy);
        Text vsText = new Text("vs");
        vsText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 25));
        HBox infoBox = new HBox(playerInfoGrid, vsText, enemyInfoGrid);
        infoBox.setSpacing(50);

        //Setting up list of events
        VBox eventList = new VBox();
        BattleEventListener eventListListener = new BattleEventListener() {
            @Override
            public void notifyBattleEvent(String message) {
                eventList.getChildren().add(new Text(message));
            }
        };

        manager.addBattleEventListener(eventListListener);

        ScrollPane scrollPane = new ScrollPane(eventList);
        scrollPane.setMinSize(50, 250);

        //Adding flavour text for starting encounter
        Text initialEncounterText = new Text(String.format("%s encounters a %s, and readies his %s!",
                player.getName(), enemy.getName(), player.getCurWeapon().getName()));
        eventList.getChildren().add(initialEncounterText);

        //Setting up information on current turn
        stateInfo = new Text();
        stateInfo.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 18));


        //Setting up buttons for player options
        Button attackButton = new Button("Attack");
        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.runAttack(player, enemy);
                manager.endTurn();
            }
        });

        Button usePotionButton = new Button("Use Potion");
        usePotionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                usePotionMenu();
            }
        });

        HBox buttons = new HBox(attackButton, usePotionButton);
        buttons.setSpacing(50);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(infoBox, scrollPane, stateInfo, buttons);
        root.setSpacing(20);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);
        menuStage.setScene(new Scene(root));

        menuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //Preventing the close request from closing the window
                windowEvent.consume();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You can't run away from a battle!");
                alert.showAndWait();
            }
        });

        manager.continueBattle();
        menuStage.showAndWait();

        //Removing the single battle event listener - this could be expanded to a class-field and method if more
        //  listeners are added in the future
        manager.removeBattleEventListener(eventListListener);
    }

    /**
     * Creates an information grid containing all the character's battle-relevant information
     * @param character Character to create grid for
     * @return information grid
     */
    public GridPane createInfoGrid(GameCharacter character) {
        GridPane infoGrid = new GridPane();
        infoGrid.setMinSize(100, 80);
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setAlignment(Pos.CENTER);

        Text nameText = new Text(character.getName());
        nameText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 14));
        Text healthText = new Text(String.format("Health: %d/%d", character.getHealth(), character.getMaxHealth()));
        Text attackText = new Text(String.format("Attack: %d - %d", character.getMinAttack(),
                character.getMaxAttack()));
        Text defenseText = new Text(String.format("Defense: %d - %d", character.getMinDefence(),
                character.getMaxDefence()));

        infoGrid.add(nameText, 0, 0);
        infoGrid.add(healthText, 0, 1);
        infoGrid.add(attackText, 0, 2);
        infoGrid.add(defenseText, 1, 2);

        infoGrid.setPadding(new Insets(5, 5, 5, 5));
        infoGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        return infoGrid;
    }

    private void usePotionMenu() {
        Stage popup = MenuUtils.createPopup(menuStage);

        Text prompt = new Text("Choose potion to use");

        Set<GameCharacter> characterSet = new HashSet<>();
        characterSet.add(player);
        characterSet.add(enemy);

        VBox potionList = new VBox();
        for (Potion potion : player.getPotionSet()) {
            Button potionButton = new Button(String.format("%s (%s)", potion.getName(), potion.getDescription()));
            potionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    potionTargetMenu(potion, characterSet);
                    popup.close();
                }
            });

            potionList.getChildren().add(potionButton);
        }
        ScrollPane scrollPane = new ScrollPane(potionList);

        VBox root = new VBox(prompt, scrollPane);
        root.setSpacing(5);
        root.setPadding(new Insets(10, 10, 10, 10));
        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    private void potionTargetMenu(Potion potion, Set<GameCharacter> targetSet) {
        Stage popup = MenuUtils.createPopup(menuStage);

        Text prompt = new Text("Choose target for potion");
        VBox targetList = new VBox();
        for (GameCharacter target : targetSet) {
            Button targetButton = new Button(target.getName());
            targetButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.usePotion(potion, target);
                    manager.continueBattle(); //FIXME is this the right order?
                    popup.close();
                }
            });

            targetList.getChildren().add(targetButton);
        }
        ScrollPane scrollPane = new ScrollPane(targetList);

        VBox root = new VBox(prompt, scrollPane);
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    public void setStateInfo(String stateMessage) {
        stateInfo.setText(stateMessage);
    }

    public void showBattleEnded(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        //Showing the battle-ending message and then exiting the battle
        alert.showAndWait();
        menuStage.close();
    }
}
