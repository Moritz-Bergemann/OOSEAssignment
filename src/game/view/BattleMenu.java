package game.view;

import game.controller.battle.BattleEventObserver;
import game.controller.battle.BattleManager;
import game.controller.RemovableObserver;
import game.model.GameCharacter;
import game.model.items.Potion;
import game.model.observers.HealthChangeObserver;
import game.model.Player;
import javafx.application.Platform;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BattleMenu {
    private Player player;
    private GameCharacter enemy;
    private BattleManager manager;
    private Stage menuStage;

    private List<RemovableObserver> observers;
    private Text stateInfo;

    public BattleMenu(Player player, GameCharacter enemy, Stage mainStage) {
        this.player = player;
        this.enemy = enemy;
        this.manager = null;
        this.observers = new LinkedList<>();
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

        //Adding observer to track each event that occurs in the battle (notified by controller)
        BattleEventObserver eventListObserver = new BattleEventObserver() {
            @Override
            public void notify(String message) {
                //Add event text
                eventList.getChildren().add(new Text(message));
            }

            @Override
            public void removeSelf() {
                manager.removeBattleEventObserver(this);
            }
        };
        manager.addBattleEventObserver(eventListObserver);
        observers.add(eventListObserver);

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

        //Initiating the first turn of the battle
        manager.continueBattle();
        menuStage.showAndWait();

        //Removing all observers
        for (RemovableObserver observer : observers) {
            observer.removeSelf();
        }
    }

    public void setStateInfo(String stateMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stateInfo.setText(stateMessage);
            }
        });

    }

    public void showBattleEnded(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        //Showing the battle-ending message and then exiting the battle
        alert.showAndWait();
        menuStage.close();
    }

    /**
     * Creates an information grid containing all the character's battle-relevant information
     * @param character Character to create grid for
     * @return information grid
     */
    private GridPane createInfoGrid(GameCharacter character) {
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

        //Creating & adding observer to update GameCharacter's health when it changes
        HealthChangeObserver healthObs = new HealthChangeObserver() {
            @Override
            public void notify(int newHealth) {
                healthText.setText(String.format("Health: %d/%d", newHealth, character.getMaxHealth()));
            }

            @Override
            public void removeSelf() {
                character.removeHealthChangeObserver(this);
            }
        };
        character.addHealthChangeObserver(healthObs); //Actually adds observer to character
        observers.add(healthObs); //Adding observer so it removes itself when the battle is over

        //Inserting information into grid
        infoGrid.add(nameText, 0, 0);
        infoGrid.add(healthText, 0, 1);
        infoGrid.add(attackText, 0, 2);
        infoGrid.add(defenseText, 1, 2);

        infoGrid.setPadding(new Insets(5, 5, 5, 5));
        infoGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        return infoGrid;
    }

    /**
     * Menu for using potion
     */
    private void usePotionMenu() {
        Stage popup = MenuUtils.createPopup(menuStage);

        Text prompt = new Text("Choose potion to use");

        Set<GameCharacter> characterSet = new HashSet<>();
        characterSet.add(player);
        characterSet.add(enemy);

        VBox potionList = new VBox();
        for (Potion potion : player.getPotionList()) {
            Button potionButton = new Button(String.format("%s (%s)", potion.getName(), potion.getDescription()));
            potionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.usePotion(potion, enemy);

                    popup.close();

                    manager.endTurn();
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
}
