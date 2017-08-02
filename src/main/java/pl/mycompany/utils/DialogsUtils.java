package pl.mycompany.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtils {

    static ResourceBundle bundle = ResourceBundle.getBundle("bundles.resourceBundles");

    public static void dialogAboutApplication() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(bundle.getString("about"));
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(bundle.getString("about.content"));
        infoAlert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmationDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("exit"));
        confirmationAlert.setHeaderText(bundle.getString("dialog.header"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static Optional<ButtonType> showConfirmationDeleteDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("confirmation.dialog"));
        confirmationAlert.setHeaderText(bundle.getString("delete.conf.title"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));
        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public static String editDialog(String value) {
        TextInputDialog editDialog = new TextInputDialog(value);
        editDialog.setTitle(bundle.getString("edit"));
        editDialog.setHeaderText(bundle.getString("edit"));
        editDialog.setContentText(bundle.getString("edit.content"));
        Optional<String> result = editDialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}