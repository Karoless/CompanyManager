package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.mycompany.database.models.CustomerLogic;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.exception.AppException;

public class AddCustomerController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField nipTextField;
    @FXML
    private Button addButton;
    @FXML
    private CustomerLogic customerLogic;

    @FXML
    public void initialize() {
        this.customerLogic = new CustomerLogic();
        bindings();
    }

    @FXML
    public void bindings() {
        this.addButton.disableProperty().bind(this.firstNameTextField.textProperty().isEmpty().or(this.lastNameTextField.textProperty().isEmpty()).or(this.addressTextField.textProperty().isEmpty()));
        this.firstNameTextField.textProperty().bindBidirectional(this.customerLogic.getCustomerFxObjectProperty().firstNameProperty());
        this.lastNameTextField.textProperty().bindBidirectional(this.customerLogic.getCustomerFxObjectProperty().lastNameProperty());
        this.addressTextField.textProperty().bindBidirectional(this.customerLogic.getCustomerFxObjectProperty().addressProperty());
        this.nipTextField.textProperty().bindBidirectional(this.customerLogic.getCustomerFxObjectProperty().nipProperty());
    }

    @FXML
    public void addCustomer() {
        try {
            this.customerLogic.saveCustomerInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        clearFields();
    }

    @FXML
    private void clearFields() {
        this.firstNameTextField.clear();
        this.lastNameTextField.clear();
        this.nipTextField.clear();
        this.addressTextField.clear();
    }

    public CustomerLogic getCustomerLogic() {
        return customerLogic;
    }
}