package View;

import Model.Items.Armour;
import Model.Items.Item;
import Model.Items.Weapon;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class MainMenu {
    private Stage mainStage;
    private Player player;

    public MainMenu(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void showMenu() {
        //Creating buttons for performing options
        Button chooseWeaponButton, chooseArmourButton, chooseNameButton, exitButton, storeButton, battleButton;
        chooseWeaponButton = new Button("Choose Weapon");
        chooseWeaponButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseWeapon();
            }
        });

        chooseArmourButton = new Button("Choose Armour");
        chooseArmourButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseArmour();
            }
        });

        chooseNameButton = new Button("Choose Name");
        chooseNameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseName();
            }
        });

        storeButton = new Button("Visit Store");
        storeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO visit store here
            }
        });

        battleButton = new Button("Next Battle");
        battleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO start battle here
            }
        });

        exitButton = new Button("Exit Game");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO exit game here
            }
        });

        GridPane playerInfoGrid = new GridPane();
        playerInfoGrid.setMinSize(100, 80);
        playerInfoGrid.setHgap(10);
        playerInfoGrid.setVgap(10);
        playerInfoGrid.setAlignment(Pos.CENTER);

        Text nameText = new Text("Name: " + player.getName());
        Text goldText = new Text("Gold: " + player.getGold());
        Text weaponText = new Text("Weapon: " + player.getCurWeapon().getName());
        Text armourText = new Text("Armour: " + player.getCurArmour().getName());
        Text healthText = new Text(String.format("Health: %d/%d", player.getHealth(), player.getMaxHealth()));
        Button inventoryButton = new Button("Show Inventory");
        inventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showInventory();
            }
        });

        playerInfoGrid.add(nameText, 0, 0);
        playerInfoGrid.add(healthText, 1, 0);
        playerInfoGrid.add(weaponText, 0, 1);
        playerInfoGrid.add(armourText, 1, 1);
        playerInfoGrid.add(goldText, 0, 2);
        playerInfoGrid.add(inventoryButton, 1, 2);
        playerInfoGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        playerInfoGrid.setPadding(new Insets(5, 5, 5, 5));

        //Creating Button Sets (each row of buttons is its own horizontal set)
        HBox chooseHBox = new HBox(chooseWeaponButton, chooseArmourButton, chooseNameButton);
        chooseHBox.setSpacing(10);
        HBox visitHBox = new HBox(storeButton, battleButton);
        visitHBox.setSpacing(10);
        HBox otherHBox = new HBox(exitButton);
        otherHBox.setSpacing(10);
        chooseHBox.setAlignment(Pos.CENTER);
        visitHBox.setAlignment(Pos.CENTER);
        otherHBox.setAlignment(Pos.CENTER);

        VBox optionsVBox = new VBox(chooseHBox, visitHBox, otherHBox);
        optionsVBox.setSpacing(10);

        VBox rootVBox = new VBox(playerInfoGrid, optionsVBox);
        rootVBox.setSpacing(40);
        rootVBox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(rootVBox); //Creating scene with this grid pane

        mainStage.setTitle("Player Menu");
        mainStage.setScene(scene);

        mainStage.show();
    }

    public void showInventory() {
        Set<Text> textSet = new HashSet<>();

        for (Item item : player.getItemSet()) {
            Text newText = new Text(item.getDescription());
            textSet.add(newText);
        }

        VBox itemList = new VBox();
        itemList.setPadding(new Insets(10, 10, 10, 10));
        itemList.getChildren().addAll(textSet);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(itemList);

        Stage popup = new Stage();
        popup.setTitle("Entire Inventory");
        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    public void chooseWeapon() {
        Set<Button> buttonSet = new HashSet<>();

        //Creating a button for each weapon and adding it to the set
        for (Weapon weapon : player.getWeaponSet()) {
            Button weaponButton = new Button(weapon.getDescription());
            weaponButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(weapon.getName() + " selected!"); //TODO what here
                }
            });

            buttonSet.add(weaponButton);
        }

        //Creating box to hold all weapons as selectable items
        VBox weaponList = new VBox();
        weaponList.setPadding(new Insets(10, 10, 10, 10));
        weaponList.getChildren().addAll(buttonSet);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(weaponList);

        Stage popup = new Stage();
        popup.setTitle("Choose Weapon");
        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    public void chooseArmour() {
        Set<Button> buttonSet = new HashSet<>();

        //Creating a button for each armour and adding it to the set
        for (Armour armour : player.getArmourSet()) { /
            Button armourButton = new Button(armour.getDescription());
            armourButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(armour.getName() + " selected!"); //TODO what here
                }
            });

            buttonSet.add(armourButton);
        }

        //Creating box to hold all weapons as selectable items
        VBox armourList = new VBox();
        armourList.setPadding(new Insets(10, 10, 10, 10));
        armourList.getChildren().addAll(buttonSet);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(armourList);

        Stage popup = new Stage();
        popup.setTitle("Choose Armour");
        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    public void chooseName() {
        TextField nameField = new TextField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String inputName = nameField.getText();
                System.out.println("User input name " + inputName);

                //Closing pop-up window after submission
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
        });

        VBox root = new VBox(nameField, submitButton);
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Stage popup = new Stage();
        popup.setTitle("Choose Name");
        popup.setScene(new Scene(root));
        popup.show();
    }
}