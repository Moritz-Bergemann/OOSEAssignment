package game.view;

import game.controller.IntermediateManager;
import game.controller.RemovableObserver;
import game.model.items.*;
import game.model.observers.*;
import game.model.Player;
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
import javafx.stage.WindowEvent;

import java.util.*;

public class IntermediateMenu {
    private Player player;
    private IntermediateManager manager;
    private Stage menuStage;

    private List<RemovableObserver> observers;

    public IntermediateMenu(Stage mainStage, Player player) {
        this.player = player;
        this.manager = null;
        menuStage = new Stage();
        menuStage.initOwner(mainStage); //Setting this menu's parent as main window

        observers = new LinkedList<>();
    }

    public void setManager(IntermediateManager manager) {
        this.manager = manager;
    }

    public void showMenu() {
        if (manager == null) {
            throw new IllegalArgumentException("No manager added to menu to perform actions!");
        }

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

        storeButton = new Button("Go to Shop");
        storeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.goToShop();
            }
        });

        battleButton = new Button("Next Battle");
        battleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.startBattle();
            }
        });

        exitButton = new Button("Exit Game");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exitPrompt();
            }
        });

        //Setting up grid showing player information
        GridPane playerInfoGrid = new GridPane();
        playerInfoGrid.setMinSize(100, 80);
        playerInfoGrid.setHgap(10);
        playerInfoGrid.setVgap(10);
        playerInfoGrid.setAlignment(Pos.CENTER);

        String name = player.getName();
        if (name == null) {
            name = "-";
        }
        Text nameText = new Text("Name: " + name);
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

        //Creating observers to update name, weapon, armour, health & gold when this occurs
        NameChangeObserver nameObs = new NameChangeObserver() {
            @Override
            public void notify(String newName) {
                nameText.setText("Name: " + newName);
            }

            @Override
            public void removeSelf() {
                player.removeNameChangeObserver(this);
            }
        };
        player.addNameChangeObserver(nameObs);
        observers.add(nameObs);

        HealthChangeObserver healthObs = new HealthChangeObserver() {
            @Override
            public void notify(int newHealth) {
                healthText.setText(String.format("Health: %d/%d", newHealth, player.getMaxHealth()));
            }

            @Override
            public void removeSelf() {
                player.removeHealthChangeObserver(this);
            }
        };
        player.addHealthChangeObserver(healthObs);
        observers.add(healthObs);

        GoldChangeObserver goldObs = new GoldChangeObserver() {
            @Override
            public void notify(int newGoldAmount) {
                goldText.setText("Gold: " + newGoldAmount);
            }

            @Override
            public void removeSelf() {
                player.removeGoldChangeObserver(this);
            }
        };
        player.addGoldChangeObserver(goldObs);
        observers.add(goldObs);

        WeaponChangeObserver weaponObs = new WeaponChangeObserver() {
            @Override
            public void notify(Weapon newWeapon) {
                String weaponName = "-";
                if (newWeapon != null) {
                    weaponName = newWeapon.getName();
                }
                weaponText.setText("Weapon: " + weaponName);
            }

            @Override
            public void removeSelf() {
                player.removeWeaponChangeObserver(this);
            }
        };
        player.addWeaponChangeObserver(weaponObs);
        observers.add(weaponObs);

        ArmourChangeObserver armourObs = new ArmourChangeObserver() {
            @Override
            public void notify(Armour newArmour) {
                String armourName = "-";
                if (newArmour != null) {
                    armourName = newArmour.getName();
                }
                armourText.setText("Weapon: " + armourName);
            }

            @Override
            public void removeSelf() {
                player.removeArmourChangeObserver(this);
            }
        };
        player.addArmourChangeObserver(armourObs);
        observers.add(armourObs);

        //Adding information grid to structured grid
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

        menuStage.setTitle("Player Menu");
        menuStage.setScene(scene);

        menuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                exitPrompt();
            }
        });

        //Run the menu and hold the program here (don't return to controller) until the window is closed
        menuStage.showAndWait();

        //Removing all observers once window closes
        for (RemovableObserver observer : observers) {
            observer.removeSelf();
        }
    }

    public void showNotReadyForBattle() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Not ready for battle");
        alert.setHeaderText(null);
        alert.setContentText("You are not yet ready for battle. To be ready for battle, you must be equipped with a " +
                "weapon, a set of armour and must have chosen your name.");
        alert.showAndWait();
    }

    public void startBattle() {
        menuStage.close();
    }

    private void showInventory() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Entire Inventory");

        VBox itemList = new VBox();
        itemList.setPadding(new Insets(10, 10, 10, 10));

        //Creating list of items
        for (Item item : player.getItemSet()) {
            Text newText = new Text(String.format("%s (%s) - %s with %d-%d effect", item.getName(),
                    item.getDescription(), item.getType(), item.getMinEffect(), item.getMaxEffect()));

            itemList.getChildren().add(newText);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(itemList);

        //Adding button to close menu
        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.close();
            }
        });

        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    private void exitPrompt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Game");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        //Creating custom option buttons and adding them to the dialog
        ButtonType quitButtonType = new ButtonType("Quit");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(quitButtonType, cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(quitButtonType)) { //If user confirmed choice to quit game
            manager.exitGame();
            menuStage.close();
        }
    }

    private void chooseWeapon() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Choose Weapon");


        //Creating box to hold all weapons as selectable items
        VBox weaponList = new VBox();
        weaponList.setPadding(new Insets(10, 10, 10, 10));

        //Creating a button for each weapon and adding it to the set
        for (Weapon weapon : player.getWeaponList()) {
            Button weaponButton = new Button(String.format("%s (%s) - %d-%d damage", weapon.getName(),
                    weapon.getDescription(), weapon.getMinEffect(), weapon.getMaxEffect()));
            weaponButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.chooseWeapon(weapon);
                    popup.close();
                }
            });

            weaponList.getChildren().add(weaponButton);
        }

        //Adding button to just remove current weapon
        Button noneButton = new Button("None (remove current weapon)");
        noneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.chooseWeapon(null);
                popup.close();
            }
        });
        weaponList.getChildren().add(noneButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(weaponList);

        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    private void chooseArmour() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Choose Armour");

        //Creating box to hold all weapons as selectable items
        VBox armourList = new VBox();
        armourList.setPadding(new Insets(10, 10, 10, 10));

        //Creating a button for each armour and adding it to the set
        for (Armour armour : player.getArmourList()) {
            Button armourButton = new Button(String.format("%s (%s) - %d-%d defence", armour.getName(),
                    armour.getDescription(), armour.getMinEffect(), armour.getMaxEffect()));
            armourButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.chooseArmour(armour);
                    popup.close();
                }
            });

            armourList.getChildren().add(armourButton);
        }

        //Adding button to just remove current weapon
        Button noneButton = new Button("None (remove current weapon)");
        noneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.chooseArmour(null);
                popup.close();
            }
        });
        armourList.getChildren().add(noneButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(armourList);

        popup.setScene(new Scene(scrollPane));
        popup.show();
    }

    private void chooseName() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Choose Name");

        TextField nameField = new TextField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String inputName = nameField.getText();
                try {
                    manager.chooseCharacterName(inputName.strip());
                }
                catch (IllegalArgumentException i) {
                    MenuUtils.showError("Invalid input", i.getMessage(), menuStage);
                }

                //Closing pop-up window after submission
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
        });

        VBox root = new VBox(nameField, submitButton);
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        popup.setScene(new Scene(root));
        popup.show();
    }
}