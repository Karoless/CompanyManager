package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.mycompany.database.models.CustomerLogic;
import pl.mycompany.modelfx.CustomerFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.exception.AppException;
import java.io.IOException;

public class ShowCustomerController {

    public static final String FXML_ADD_CUSTOMER_PATH = "/fxml/AddCustomer.fxml";

    @FXML
    private MenuItem editMenuItem;
    @FXML
    private MenuItem addMenuItem;
    @FXML
    private TableView<CustomerFx> tableView;
    @FXML
    private TableColumn<CustomerFx, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerFx, String> lastNameColumn;
    @FXML
    private TableColumn<CustomerFx, String> addressColumn;
    @FXML
    private TableColumn<CustomerFx, String> nipColumn;
    @FXML
    private MenuItem deleteMenuItem;

    private CustomerLogic customerLogic;

    @FXML
    public void initialize() {
        createLogic();
        initTableView();
        this.deleteMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());
        this.editMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void initTableView() {
        this.tableView.setItems(this.customerLogic.getCustomerFxObservableList());

        this.firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        this.addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        this.nipColumn.setCellValueFactory(cellData -> cellData.getValue().nipProperty());

        this.firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nipColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.customerLogic.setEditCustomerFxObjectProperty(newValue);
        });
    }

    @FXML
    public void onEditCommitFirstName(TableColumn.CellEditEvent<CustomerFx, String> customerFxStringCellEditEvent) {
        this.customerLogic.getEditCustomerFxObjectProperty().setFirstName(customerFxStringCellEditEvent.getNewValue());
        try {
            this.customerLogic.saveEditCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void onEditCommitLastName(TableColumn.CellEditEvent<CustomerFx, String> customerFxStringCellEditEvent) {
        this.customerLogic.getEditCustomerFxObjectProperty().setLastName(customerFxStringCellEditEvent.getNewValue());
        try {
            this.customerLogic.saveEditCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void onEditCommitAddress(TableColumn.CellEditEvent<CustomerFx, String> customerFxStringCellEditEvent) {
        this.customerLogic.getEditCustomerFxObjectProperty().setAddress(customerFxStringCellEditEvent.getNewValue());
        try {
            this.customerLogic.saveEditCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void onEditCommitNip(TableColumn.CellEditEvent<CustomerFx, String> customerFxStringCellEditEvent) {
        this.customerLogic.getEditCustomerFxObjectProperty().setNip(customerFxStringCellEditEvent.getNewValue());
        try {
            this.customerLogic.saveEditCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void deleteCustomer() {
        try {
            this.customerLogic.deleteCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void addCustomer()   {
        FXMLLoader loader = FxmlUtils.getLoader(FXML_ADD_CUSTOMER_PATH);
        Scene scene = null;

            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }

            AddCustomerController controller =loader.getController();
            controller.getCustomerLogic().setCustomerFxObjectProperty(new CustomerFx());
            controller.bindings();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        try {
            this.customerLogic.init();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editCustomer() {
        FXMLLoader loader = FxmlUtils.getLoader(FXML_ADD_CUSTOMER_PATH);
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }

        AddCustomerController controller =loader.getController();
        CustomerFx customerFx = this.tableView.getSelectionModel().getSelectedItem();
        controller.getCustomerLogic().setCustomerFxObjectProperty(customerFx);
        controller.bindings();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        try {
            this.customerLogic.init();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    private void createLogic() {
        this.customerLogic = new CustomerLogic();
        try {
            this.customerLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}