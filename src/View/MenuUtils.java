package View;

import javafx.scene.control.Alert;
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
        popup.initModality(Modality.WINDOW_MODAL);

        return popup;
    }

    public static void showError(String title, String message, Stage parent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(parent);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showMessage(String title, String message, Stage parent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(parent);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
