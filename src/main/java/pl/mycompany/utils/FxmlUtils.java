package pl.mycompany.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;

public class FxmlUtils {

    public static Pane fxmlLoader(String pathToFxml) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(pathToFxml));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (Exception e) {
           DialogsUtils.errorDialog(e.getMessage());
        }
        return null;
    }

    public static FXMLLoader getLoader(String pathToFxml) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(pathToFxml));
        loader.setResources(getResourceBundle());

            return loader;
    }

    public static ResourceBundle getResourceBundle() {
            return ResourceBundle.getBundle("bundles.resourceBundles");
    }
    public static void integerValidation(TextField valueTextField) {
        valueTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                if(!valueTextField.getText().matches("[1-9]*"))
                {
                    wrongTypeOfDataSignal(valueTextField);
                }
        } );
    }
    public static void doubleValidation(TextField priceTextField) {
        priceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                if(!priceTextField.getText().matches("[1-9]*|[0-9]*(\\,[0-9]*)"))
                {
                    wrongTypeOfDataSignal(priceTextField);
                }
        } );
    }

    private static void wrongTypeOfDataSignal(TextField priceTextField) {
        priceTextField.setStyle("-fx-background-color: red;");
        DialogsUtils.errorDialog(FxmlUtils.getResourceBundle().getString("error.datatype"));
        priceTextField.clear();
        priceTextField.setStyle("-fx-background-color: white;");
    }
}