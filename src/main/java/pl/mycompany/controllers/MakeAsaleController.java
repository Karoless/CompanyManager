package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.util.converter.NumberStringConverter;
import pl.mycompany.database.models.*;
import pl.mycompany.modelfx.SaleFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.exception.AppException;
import javafx.beans.binding.Bindings;
import java.sql.SQLException;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

public class MakeAsaleController {

    @FXML
    private TableView<SaleFx> SalesTableView;
    @FXML
    private TableColumn<SaleFx, String> ItemsNameColumn;
    @FXML
    private TableColumn<SaleFx, Integer> valueOfItemsColumn;
    @FXML
    private TableColumn<SaleFx, Double> priceOfItemsColumn;
    @FXML
    private TextArea availabilityTextArea;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private TextArea priceTextField;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private ComboBox itemComboBox;
    @FXML
    private TextField valueTextField;
    @FXML
    private Button addItem;
    @FXML
    private Button makeASale;

    private CategoryLogic categoryLogic;
    private SaleLogic saleLogic;
    private ItemLogic itemLogic;
    private CustomerLogic customerLogic;
    private SaleListLogic saleListLogic;

    @FXML
    public void initialize() {
        createAllLogic();
        initColumnTable();
        initComboBoxes();
        binding();
        disableProperty();
    }

    private void disableProperty() {
        this.deleteMenuItem.disableProperty().bind(this.SalesTableView.getSelectionModel().selectedItemProperty().isNull());
        this.addItem.disableProperty().bind((this.valueTextField.textProperty().isEqualTo("0").or(this.itemComboBox.getSelectionModel().selectedItemProperty().isNull())));
        this.makeASale.disableProperty().bind(this.customerComboBox.valueProperty().isNull().or(this.priceTextField.textProperty().isEqualTo("0 zł").or(this.priceTextField.textProperty().isEqualTo(""))));
        FxmlUtils.integerValidation(valueTextField);
        this.valueTextField.disableProperty().bind(this.itemComboBox.valueProperty().isNull());
    }

    @FXML
    private void initComboBoxes() {
        this.categoryComboBox.setItems(this.itemLogic.getCategoryList());
        this.customerComboBox.setItems(this.customerLogic.getStringObservableList());
        this.itemComboBox.setItems(this.itemLogic.getStringObservableList());

    }

    @FXML
    private void createAllLogic() {
        this.categoryLogic = new CategoryLogic();
        this.saleLogic = new SaleLogic();
        this.itemLogic = new ItemLogic();
        this.customerLogic = new CustomerLogic();
        this.saleListLogic = new SaleListLogic();

        try {
            this.categoryLogic.init();
            this.itemLogic.init();
            this.customerLogic.init();
            this.saleLogic.init();
            this.saleListLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    private void initColumnTable() {
        this.SalesTableView.setItems(this.saleLogic.getSaleFxObservableList());
        this.ItemsNameColumn.setCellValueFactory(cellData-> cellData.getValue().boughtItemsProperty());
        this.priceOfItemsColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        this.valueOfItemsColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfBoughtItemsProperty().asObject());
        this.SalesTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                this.saleLogic.setEditSaleFxObjectProperty(newValue)));
    }

    private void binding() {
        this.itemLogic.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        Bindings.bindBidirectional(this.itemComboBox.valueProperty(),this.saleLogic.getSaleFxObjectProperty().boughtItemsProperty());
        Bindings.bindBidirectional(this.customerComboBox.valueProperty(),this.saleListLogic.getSaleListFxObjectProperty().customerProperty());
        Bindings.bindBidirectional(this.valueTextField.textProperty(),this.saleLogic.getSaleFxObjectProperty().numberOfBoughtItemsProperty(),new NumberStringConverter());
    }

    @FXML
    public void chooseCategoryComboBox() {
        this.itemLogic.filterByCategory();
    }

    @FXML
    public void clearCategory() {
        this.categoryComboBox.getSelectionModel().clearSelection();
        try {
            this.itemLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void chooseItemComboBox() {
        if(!itemComboBox.getSelectionModel().isEmpty()) {
            int i = 0;

            try {
                i = itemLogic.checkItemAvailability(saleLogic.getSaleFxObjectProperty().getBoughtItems(), itemLogic.getItemList());
            } catch (AppException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
            String stringValue = i + " sztuk";
            this.availabilityTextArea.setText(stringValue);
        }
    }

    @FXML
    public void AddItemToSale() {

        try {
            double itemPrice = itemLogic.checkPriceOfItem(saleLogic.getSaleFxObjectProperty().getBoughtItems(),itemLogic.getItemList());
            double totalItemPrice = saleLogic.getSaleFxObjectProperty().getNumberOfBoughtItems()*itemPrice;
            this.saleLogic.getSaleFxObjectProperty().setTotalPrice(totalItemPrice);
            if(this.itemLogic.checkAvailabilityAndUpdateData(saleLogic.getSaleFxObjectProperty().getBoughtItems(),itemLogic.getItemList(),saleLogic.getSaleFxObjectProperty().getNumberOfBoughtItems()))
            this.saleLogic.saveSaleInDatabase();
            this.priceTextField.setText(this.saleLogic.getTotalPriceOfSale());
        } catch (AppException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        clearFields();
    }
    @FXML
    public void clearFields() {
        this.valueTextField.clear();
        this.itemComboBox.getSelectionModel().clearSelection();
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.availabilityTextArea.clear();
    }

    @FXML
    public void ConfirmSale() {
            try {
                this.saleListLogic.prepareSaleToSave();
                this.saleLogic.finishSale(this.saleLogic.getSaleList());
            } catch (AppException | SQLException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
            this.priceTextField.clear();
            this.SalesTableView.getColumns().clear();
            this.customerComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void deleteItem() {
        try {
            this.itemLogic.updateAfterRemoverItemFromSale(saleLogic.getEditSaleFxObjectProperty().getBoughtItems(),itemLogic.getItemList(),saleLogic.getEditSaleFxObjectProperty().getNumberOfBoughtItems());
            this.saleLogic.deleteSalesInDataBase();
            this.priceTextField.setText(this.saleLogic.getTotalPriceOfSale());
        } catch (AppException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}