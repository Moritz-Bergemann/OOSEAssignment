package game.view;

import game.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuUtils {

    /**
     * Creates a new window that behaves as a 'popup' (blocks access to any parent windows)
     * @param parent parent window of the popup
     * @return the created popup window as a Stage
     */
    public static Stage createPopup(Stage parent) {
        Stage popup = new Stage();
        popup.initOwner(parent);
        popup.initModality(Modality.APPLICATION_MODAL);

        return popup;
    }

    /**
     * Displays an error to the user
     * @param title title of error message
     * @param message content of error message
     * @param parent parent window of error message
     */
    public static void showError(String title, String message, Stage parent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(parent);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Displays a message to the user in generic format
     * @param title message title
     * @param message message content
     * @param parent parent window
     */
    public static void showMessage(String title, String message, Stage parent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(parent);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Displays a final message for when the game has ended. Message content changes depending on if player is alive or
     * dead
     * @param player player of game that has ended
     */
    public static void gameEndedMenu(Player player) {
        Stage menuStage = new Stage();

        Text headerText, lowerText;

        //Creating message for if player won or lost
        if (player.wonGame()) { //If player won
            menuStage.setTitle("Victory!");
            headerText = new Text("You are victorious!");
            lowerText = new Text(String.format("%s will be remembered as a hero.", player.getName()));
        }
        else { //If player lost
            menuStage.setTitle("Defeat!");
            headerText = new Text("You have been defeated!");
            lowerText = new Text(String.format("Rest in peace, %s.", player.getName()));
        }
        headerText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 25));

        //Creating final game statistics
        HBox stats = new HBox(
                new Text("Final Gold: " + player.getGold()),
                new Text("Final Weapon: " + player.getCurWeapon().getName()),
                new Text("Final Armour: " + player.getCurArmour().getName())
        );
        stats.setSpacing(10);

        Button quitButton = new Button("Exit Game");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuStage.close();
            }
        });

        VBox root = new VBox(headerText, lowerText, stats, quitButton);
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);

        menuStage.setScene(new Scene(root));
        menuStage.showAndWait();
    }

    /**
     * Logs a non-fatal/expected error
     * @param message error message
     */
    public static void logError(String message) {
        //Currently just prints to terminal, could be changed to writing to logfile in the future
        System.out.println("ERROR LOG: " + message);
    }
}
