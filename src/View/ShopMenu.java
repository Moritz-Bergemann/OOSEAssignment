package View;

import Model.Items.Item;
import Model.Player;
import Model.Shop;
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

import javax.naming.Name;

public class ShopMenu {
    private Shop shop;
    private Player player;
    private Stage menuStage;

    public ShopMenu(Stage parentStage, Shop shop, Player player) {
        this.player = player;
        this.shop = shop;
        menuStage = new Stage();
        menuStage.initOwner(parentStage);
        menuStage.initModality(Modality.APPLICATION_MODAL); /*Ensures player cannot interact with intermediate menu
            while in the shop*/
    }

    public void runMenu() {
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

        //WONKY STARTS HERE
        TableColumn<Item, String> buttonColumn = new TableColumn("Purchase Item");

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
                                System.out.println("selectedData: " + item);
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
        //WONKY ENDS HERE

        for (Item item : shop.getCurrentStock()) {
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
                sellItemMenu(); //TODO
            }
        });

        enchantWeaponButton = new Button("Enchant Weapon");
        enchantWeaponButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                enchantWeaponMenu(); //TODO
            }
        });

        leaveButton = new Button("Leave The Shop");
        leaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                leaveShop(); //TODO
            }
        });
        HBox buttons = new HBox(sellItemButton, enchantWeaponButton, leaveButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(5, 10, 5, 10));

        //Creating title text for shop
        Text title = new Text("Steve's Emporium");
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox root = new VBox(title, table, buttons);
        Scene scene = new Scene(root);

        menuStage.setScene(scene);
        menuStage.showAndWait();
    }

    private void leaveShop() {
    }

    private void enchantWeaponMenu() {
    }

    private void sellItemMenu() {
    }

    public void showPopup(String format) {
    }
}
