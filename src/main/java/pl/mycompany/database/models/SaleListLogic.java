package pl.mycompany.database.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mycompany.database.dao.SaleListDao;
import pl.mycompany.modelfx.SaleListFx;
import pl.mycompany.utils.conventers.SaleListConverter;
import pl.mycompany.utils.exception.AppException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleListLogic {
    private ObjectProperty<SaleListFx> saleListFxObjectProperty = new SimpleObjectProperty<>(new SaleListFx());
    private ObjectProperty<SaleListFx> editSaleListFxObjectProperty = new SimpleObjectProperty<>(new SaleListFx());
    private ObservableList<SaleListFx> saleListFxObservableList = FXCollections.observableArrayList();
    private List<SaleListFx> saleFXList = new ArrayList<>();

    public void init() throws AppException {
        SaleListDao saleListDao = new SaleListDao();
        List<SaleList> saleList = saleListDao.queryForAll(SaleList.class);
        this.saleFXList.clear();
        saleList.forEach(sale -> {
            SaleListFx saleListFx = SaleListConverter.convertToFx(sale);
            this.saleFXList.add(saleListFx);
        });
        this.saleListFxObservableList.setAll(saleFXList);
    }

    private void saveData() throws AppException {
        SaleListDao saleListDao = new SaleListDao();
        SaleList saleList = SaleListConverter.convertSaleListFxToCustomer(this.getSaleListFxObjectProperty());
        saleListDao.createOrUpdate(saleList);
        this.init();
    }

    public void prepareSaleToSave() throws AppException, SQLException {
        SaleLogic saleLogic = new SaleLogic();
        this.getSaleListFxObjectProperty().setDateOfSale(LocalDate.now());
        System.out.println(LocalDate.now());
        this.getSaleListFxObjectProperty().setItem_list(saleLogic.getListOfSoldItems());
        System.out.println(getSaleListFxObjectProperty().getItem_list());
        this.getSaleListFxObjectProperty().setValueOfItems(saleLogic.getValueOfSoldItems());
        this.getSaleListFxObjectProperty().setTotalCostOfItem(saleLogic.getPriceOfSoldItems());
        this.getSaleListFxObjectProperty().setTotalPrice(saleLogic.getTotalPriceOfSale());
        saveData();
    }

    public SaleListFx getSaleListFxObjectProperty() {
        return saleListFxObjectProperty.get();
    }

    public ObjectProperty<SaleListFx> saleListFxObjectPropertyProperty() {
        return saleListFxObjectProperty;
    }

    public void setSaleListFxObjectProperty(SaleListFx saleListFxObjectProperty) {
        this.saleListFxObjectProperty.set(saleListFxObjectProperty);
    }

    public SaleListFx getEditSaleListFxObjectProperty() {
        return editSaleListFxObjectProperty.get();
    }

    public ObjectProperty<SaleListFx> editSaleListFxObjectPropertyProperty() {
        return editSaleListFxObjectProperty;
    }

    public void setEditSaleListFxObjectProperty(SaleListFx editSaleListFxObjectProperty) {
        this.editSaleListFxObjectProperty.set(editSaleListFxObjectProperty);
    }

    public ObservableList<SaleListFx> getSaleListFxObservableList() {
        return saleListFxObservableList;
    }

    public void setSaleListFxObservableList(ObservableList<SaleListFx> saleListFxObservableList) {
        this.saleListFxObservableList = saleListFxObservableList;
    }

    public List<SaleListFx> getSaleFXList() {
        return saleFXList;
    }

    public void setSaleFXList(List<SaleListFx> saleFXList) {
        this.saleFXList = saleFXList;
    }

}