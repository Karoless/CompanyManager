package pl.mycompany;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.mycompany.database.dbuilts.DbManager;
import pl.mycompany.utils.FxmlUtils;

public class Main extends Application {

    public static final String FXML_BORDER_PANE_MAIN_PATH = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane borderPane = FxmlUtils.fxmlLoader(FXML_BORDER_PANE_MAIN_PATH);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.show();
        DbManager.initDatabase();
    }
}