package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import pl.mycompany.database.models.ItemLogic;
import pl.mycompany.modelfx.CategoryFx;
import pl.mycompany.modelfx.ItemFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.exception.AppException;
import java.io.IOException;

public class ShowItemsController {

    public static final String FXML_ADD_CUSTOMER_PATH = "/fxml/AddItem.fxml";

    @FXML
    private MenuItem editMenuItem;
    @FXML
    private MenuItem addMenuItem;
    @FXML
    private MenuItem deleteIMenuItem;
    @FXML
    private TableView<ItemFx> tableView;
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private TableColumn<ItemFx, String> nameColumn;
    @FXML
    private TableColumn<ItemFx, Double> priceColumn;
    @FXML
    private TableColumn<ItemFx, CategoryFx> categoryColumn;
    @FXML
    private TableColumn<ItemFx, Integer> numberColumn;

    private ItemLogic itemLogic;

    @FXML
    public void initialize() {
        createLogic();
        this.categoryComboBox.setItems(this.itemLogic.getCategoryList());
        initTable();
    }

    @FXML
    private void initTable() {
        this.tableView.setItems(this.itemLogic.getItemFxObservableList());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxObjectPropertyProperty());
        this.priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        this.numberColumn.setCellValueFactory(cellData-> cellData.getValue().valueProperty().asObject());
        this.itemLogic.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.deleteIMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());
        this.editMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        this.numberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
        this.itemLogic.setEditItemFxObjectProperty(newValue)));
    }

    @FXML
    public void filterOnActionComboBox() {
        this.itemLogic.filterByCategory();
    }

    @FXML
    public void clearCategoryBox() {
        categoryComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void deleteItem() {
        System.out.println(this.itemLogic.getEditItemFxObjectProperty().getId());
        try {
            this.itemLogic.deleteItemInDataBase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void OnEditCommitName(TableColumn.CellEditEvent<ItemFx, String> itemFxStringCellEditEvent) {
        this.itemLogic.getEditItemFxObjectProperty().setName(itemFxStringCellEditEvent.getNewValue());
        try {
            this.itemLogic.saveEditItemInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void OnEditCommitPrice(TableColumn.CellEditEvent<ItemFx, Double> itemFxDoubleCellEditEvent) {
        this.itemLogic.getEditItemFxObjectProperty().setPrice(itemFxDoubleCellEditEvent.getNewValue());
        try {
            this.itemLogic.saveEditItemInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void OnEditCommitNumberOfItem(TableColumn.CellEditEvent<ItemFx, Integer> itemFxIntegerCellEditEvent) {
        this.itemLogic.getEditItemFxObjectProperty().setValue(itemFxIntegerCellEditEvent.getNewValue());
        try {
            this.itemLogic.saveEditItemInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void addItem() throws AppException {
        FXMLLoader loader = FxmlUtils.getLoader(FXML_ADD_CUSTOMER_PATH);
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        AddItemController controller =loader.getController();
        controller.getItemLogic().setItemFxObjectProperty(new ItemFx());
        controller.bindings();
        try {
            controller.getItemLogic().init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        this.itemLogic.init();

    }
    @FXML
    public void editItem() {
        FXMLLoader loader = FxmlUtils.getLoader(FXML_ADD_CUSTOMER_PATH);
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        AddItemController controller =loader.getController();
        ItemFx itemFx = this.tableView.getSelectionModel().getSelectedItem();
        controller.getItemLogic().setItemFxObjectProperty(itemFx);
        controller.bindings();
        try {
            controller.getItemLogic().init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        try {
            this.itemLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private void createLogic() {
        this.itemLogic = new ItemLogic();
        try {
            this.itemLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}