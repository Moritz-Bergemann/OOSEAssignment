package View;

import Controller.RemovableObserver;
import Controller.ShopManager;
import Controller.StockManager;
import Model.Items.Item;
import Model.Items.Weapon;
import Model.Observers.GoldChangeObserver;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.LinkedList;
import java.util.List;

public class ShopMenu {
    private StockManager stock;
    private Player player;
    private Stage menuStage;
    private ShopManager manager;

    private List<RemovableObserver> observers;

    public ShopMenu(Stage parentStage, StockManager stock, Player player) {
        this.player = player;
        this.stock = stock;
        this.manager = null;
        menuStage = new Stage();
        menuStage.initOwner(parentStage);
        menuStage.initModality(Modality.APPLICATION_MODAL); /*Ensures player cannot interact with intermediate menu
            while in the shop*/

        observers = new LinkedList<>();
    }

    public void setManager(ShopManager manager) {
        this.manager = manager;
    }

    public void runMenu() {
        if (manager == null) {
            throw new IllegalArgumentException("Shop menu manager not set");
        }

        menuStage.setTitle("Shop");

        //Creating columns that will automatically call getter methods of added item objects
        TableView<Item> table = new TableView<>();
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Item, String> minEffectColumn = new TableColumn<>("Minimum Effect");
        minEffectColumn.setCellValueFactory(new PropertyValueFactory<>("minEffect"));
        TableColumn<Item, String> maxEffectColumn = new TableColumn<>("Maximum Effect");
        maxEffectColumn.setCellValueFactory(new PropertyValueFactory<>("maxEffect"));
        TableColumn<Item, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        //Adding columns to table
        table.getColumns().add(nameColumn);
        table.getColumns().add(descColumn);
        table.getColumns().add(minEffectColumn);
        table.getColumns().add(maxEffectColumn);
        table.getColumns().add(costColumn);

        //Adding column for purchase item button to table (adapted from https://riptutorial.com/javafx/example/27946/add-button-to-tableview)
        TableColumn<Item, String> buttonColumn = new TableColumn<>("Purchase Item");

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Item, String> call(final TableColumn<Item, String> param) {
                final TableCell<Item, String> cell = new TableCell<Item, String>() {

                    private final Button button = new Button("Buy");

                    {
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Item item = getTableView().getItems().get(getIndex());
                                System.out.println("DEBUG Selected Item: " + item.getName());

                                buyItemPrompt(item);
                            }
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        buttonColumn.setCellFactory(cellFactory);

        table.getColumns().add(buttonColumn);


        //Adding items to created table
        for (Item item : stock.getLoadedStock()) {
            table.getItems().add(item);
        }

        ScrollPane tableScroll = new ScrollPane();
        tableScroll.setContent(table);

        //Setting up bottom buttons
        Button sellItemButton, enchantWeaponButton, leaveButton;
        sellItemButton = new Button("Sell Item");
        sellItemButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sellItemMenu();
            }
        });

        enchantWeaponButton = new Button("Enchant Weapon");
        enchantWeaponButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseWeaponForEnchantmentMenu();
            }
        });

        leaveButton = new Button("Leave The Shop");
        leaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuStage.close();
            }
        });
        HBox buttons = new HBox(sellItemButton, enchantWeaponButton, leaveButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(5, 10, 5, 10));

        //Creating title & subtitle text for shop
        Text title = new Text("Steve's Emporium");
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));

        Text subtitile = new Text("Welcome to the shop! Here's everything we've got for sale at the moment.");

        VBox titleBox = new VBox(title, subtitile);

        //Creating text showing current gold
        Text goldAmount = new Text(String.format("Current gold - %d", player.getGold()));

        //Adding observer for player's current gold
        GoldChangeObserver goldObs = new GoldChangeObserver() {
            @Override
            public void notify(int newGoldAmount) {
                goldAmount.setText(String.format("Current gold - %d", newGoldAmount));
            }

            @Override
            public void removeSelf() {
                player.removeGoldChangeObserver(this);
            }
        };
        player.addGoldChangeObserver(goldObs);
        observers.add(goldObs);

        VBox root = new VBox(titleBox, table, goldAmount, buttons);
        root.setSpacing(10);
        Scene scene = new Scene(root);

        menuStage.setWidth(800); //Ensures all columns of shop are shown in window
        menuStage.setScene(scene);
        menuStage.showAndWait();

        for (RemovableObserver observer : observers) {
            observer.removeSelf();
        }
    }

    private void buyItemPrompt(Item item) {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Confirm Purchase");

        //Creating item info
        Text itemText = new Text(String.format("%s (%s) - %s", item.getName(), item.getType(), item.getDescription()));

        Text confirmText = new Text("Are you sure you want to buy this?");

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.purchaseItem(item, player);
                popup.close();
            }
        });

        Button noButton = new Button("Cancel");
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.close();
            }
        });

        HBox buttons = new HBox(yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        VBox root = new VBox(itemText, confirmText, buttons);
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);

        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    private void enchantWeaponMenu(Weapon weapon, Stage parent) {
        Stage popup = MenuUtils.createPopup(parent);
        popup.setTitle("Choose Enchantment");

        Text promptText = new Text(String.format("What enchantment do you want to apply to '%s'?", weapon.getName()));

        //Add button for each existing enchantment that initiates enchantment purchase
        VBox enchantmentList = new VBox();
        for (String enchantmentName : manager.getAllEnchantmentNames()) {
            Button enchantmentButton = new Button(String.format("%s (%s) - %s gold", enchantmentName,
                    manager.getEnchantmentDescription(enchantmentName), manager.getEnchantmentCost(enchantmentName)));
            enchantmentButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.purchaseEnchantment(player, weapon, enchantmentName);
                    popup.close();
                }
            });

            enchantmentList.getChildren().add(enchantmentButton);
        }

        //Adding scrollpane for enchantment list
        ScrollPane scrollPane = new ScrollPane(enchantmentList);

        VBox root = new VBox(promptText, scrollPane);
        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    private void chooseWeaponForEnchantmentMenu() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Choose Weapon");

        Text promptText = new Text("What weapon do you want to enchant?\nI'll need to take the weapon from your " +
                "hands to apply the enchantment, so it can't be the one you've got equipped right now.");
        VBox weaponList = new VBox();

        Weapon chosenWeapon = null;

        //Creating a button for each weapon and adding it to the list
        for (Weapon weapon : player.getWeaponList()) {
            Button weaponButton = new Button(String.format("%s (%s) - %s to %s damage", weapon.getName(),
                    weapon.getDescription(), weapon.getMinEffect(), weapon.getMaxEffect()));
            weaponButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    enchantWeaponMenu(weapon, popup);
                    popup.close();
                }
            });
            weaponList.getChildren().add(weaponButton);
        }

        //Creating box to hold all weapons as selectable items
        weaponList.setPadding(new Insets(10, 10, 10, 10));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(weaponList);

        VBox root = new VBox(promptText, scrollPane);
        root.setPadding(new Insets(10, 10, 10, 10));

        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    private void sellItemMenu() {
        Stage popup = MenuUtils.createPopup(menuStage);
        popup.setTitle("Sell Item");

        Text promptText = new Text("What item do you want to sell?");

        VBox itemList = new VBox();
        for (Item item : player.getItemSet()) {
            Button itemButton = new Button(String.format("%s (%s) - sells for %s gold", item.getName(),
                    item.getDescription(), item.getCost() / 2));
            itemButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manager.sellItem(player, item);
                    popup.close();
                }
            });

            itemList.getChildren().add(itemButton);
        }

        ScrollPane scrollPane = new ScrollPane(itemList);

        VBox root = new VBox(promptText, scrollPane);

        popup.setScene(new Scene(root));
        popup.showAndWait();
    }

    public void showMessage(String message) {
        MenuUtils.showMessage("Shop notice", message, menuStage);
    }
}