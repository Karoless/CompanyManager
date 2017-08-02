package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class LeftMenuButtonsController {

    public static final String FXML_ADD_ITEM_PATH = "/fxml/AddItem.fxml";
    public static final String FXML_ADD_CUSTOMER_PATH = "/fxml/AddCustomer.fxml";
    public static final String FXML_SHOW_CUSTOMERS_PATH = "/fxml/ShowCustomers.fxml";
    public static final String FXML_MAKE_ASALE_PATH = "/fxml/MakeAsale.fxml";
    public static final String FXML_SHOW_SALES_LIST_PATH = "/fxml/ShowSalesList.fxml";
    public static final String FXML_SHOW_ITEMS_PATH = "/fxml/ShowItems.fxml";
    @FXML
    private ToggleButton saleButton;
    @FXML
    private ToggleButton showItemsButton;
    @FXML
    private ToggleButton showCustomerButton;
    @FXML
    private ToggleButton saleListButton;
    @FXML
    private ToggleButton addItemButton;
    @FXML
    private ToggleButton addCustomerButton;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void addItem() {
        mainController.setCenter(FXML_ADD_ITEM_PATH);
    }

    @FXML
    public void showItems() {
        mainController.setCenter(FXML_SHOW_ITEMS_PATH);
    }

    @FXML
    public void addCustomer() {
        mainController.setCenter(FXML_ADD_CUSTOMER_PATH);
    }

    @FXML
    public void showCustomers() {
        mainController.setCenter(FXML_SHOW_CUSTOMERS_PATH);
    }

    @FXML
    public void makeAsale() {
        mainController.setCenter(FXML_MAKE_ASALE_PATH);
    }

    @FXML
    public void showSalesList() {
        mainController.setCenter(FXML_SHOW_SALES_LIST_PATH);
    }

}