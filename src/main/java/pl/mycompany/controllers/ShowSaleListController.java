package pl.mycompany.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.mycompany.database.models.SaleListLogic;
import pl.mycompany.modelfx.SaleListFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.exception.AppException;

import java.time.LocalDate;

public class ShowSaleListController {

    @FXML
    private TableView<SaleListFx> saleListTableView;
    @FXML
    private TableColumn<SaleListFx, LocalDate> dateTableColumn;
    @FXML
    private TableColumn<SaleListFx, String>  customerTableColumn;
    @FXML
    private TableColumn<SaleListFx, String>  itemsTableColumn;
    @FXML
    private TableColumn<SaleListFx, String>  numberTableColumn;
    @FXML
    private TableColumn<SaleListFx, String>  priceTableColumn;
    @FXML
    private TableColumn<SaleListFx, String>  totalPriceTableColumn;

    private SaleListLogic saleListLogic;

    @FXML
    public void initialize() {
        createLogic();
        initTableView();
    }

    @FXML
    private void initTableView() {
        this.saleListTableView.setItems(this.saleListLogic.getSaleListFxObservableList());
        this.dateTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfSaleProperty());
        this.customerTableColumn.setCellValueFactory(cellData ->cellData.getValue().customerProperty());
        this.itemsTableColumn.setCellValueFactory(cellData -> cellData.getValue().item_listProperty());
        this.numberTableColumn.setCellValueFactory(cellData -> cellData.getValue().valueOfItemsProperty());
        this.priceTableColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostOfItemProperty());
        this.totalPriceTableColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
    }

    private void createLogic() {
        this.saleListLogic = new SaleListLogic();
        try {
            this.saleListLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}