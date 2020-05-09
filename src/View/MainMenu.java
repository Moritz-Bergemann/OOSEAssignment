package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class MainMenu {
    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void showMenu() {
        stage.show();
    }
}

// TESTED STUFF
import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Group;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;

        import java.util.HashSet;
        import java.util.Set;

public class Main extends Application {
    static Set<String> weaponList;
    static Set<String> armourList;

    @Override
    public void start(Stage stage) throws Exception {

        //FOR SIMULATING GAME ELEMENTS//
        weaponList = new HashSet<>();
        weaponList.add("Weapon1");
        weaponList.add("Weapon2 - this one has an extremely long name and \n a line break in it! how about that");
        weaponList.add("Weapon3");
        weaponList.add("Weapon4");
        weaponList.add("Weapon5");
        weaponList.add("Weapon6");
        weaponList.add("Weapon7");
        weaponList.add("Weapon8");
        weaponList.add("Weapon9");
        weaponList.add("Weapon10");
        weaponList.add("Weapon11");
        weaponList.add("Weapon12");

        armourList = new HashSet<>();
        armourList.add("Armour1");
        armourList.add("Armour2");
        armourList.add("Armour3");
        armourList.add("Armour4");
        armourList.add("Armour5");
        armourList.add("Armour6");
        armourList.add("Armour7");

        //ACTUAL CODE STARTS HERE
        stage.setTitle("Test");

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

        Text nameText = new Text("Name: " + "PLAYER_NAME");
        Text goldText = new Text("Gold: " + "PLAYER_GOLD");
        Text weaponText = new Text("Weapon: " + "PLAYER_WEAPON");
        Text armourText = new Text("Armour: " + "PLAYER_ARMOUR");
        Text healthText = new Text("Health: " + "PLAYER_HEALTH");
        Button inventoryButton = new Button("Show Inventory");

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

        stage.setTitle("Player Menu");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void chooseWeapon() {
        Set<Button> buttonSet = new HashSet<>();

        //Creating a button for each weapon and adding it to the set
        for (String str : weaponList) { //TODO change this to weapon
            Button weaponButton = new Button(str);
            weaponButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(str + " selected!");
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
        for (String str : armourList) { //TODO change this to armour
            Button armourButton = new Button(str);
            armourButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(str + " selected!"); //TODO catch exception
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