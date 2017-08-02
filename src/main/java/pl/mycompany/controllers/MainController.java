package pl.mycompany.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;

public class MainController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private  LeftMenuButtonsController leftMenuButtonsController;

    @FXML
    private void initialize() {
        leftMenuButtonsController.setMainController(this);
        setCenter("/fxml/MakeASale.fxml/");
    }

    @FXML
    public void setCenter(String pathToFxml) {
        borderPane.setCenter(FxmlUtils.fxmlLoader(pathToFxml));
    }

    @FXML
    public void closeApplication() {
        if (DialogsUtils.showConfirmationDialog().get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
         }
    }
    @FXML
    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    @FXML
    public void setModena() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    @FXML
    public void setAlwaysOnTop(ActionEvent actionEvent) {
       Stage stage = (Stage) borderPane.getScene().getWindow();
       boolean value = ((CheckMenuItem) actionEvent.getSource()).selectedProperty().get();
       stage.setAlwaysOnTop(value);
    }

    @FXML
    public void about() {
        DialogsUtils.dialogAboutApplication();
    }
}