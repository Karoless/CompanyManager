package pl.mycompany.database.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mycompany.database.dao.SaleDao;
import pl.mycompany.modelfx.SaleFx;
import pl.mycompany.utils.conventers.SaleConverter;
import pl.mycompany.utils.exception.AppException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleLogic {

    private ObjectProperty<SaleFx> saleFxObjectProperty = new SimpleObjectProperty<>(new SaleFx());
    private ObjectProperty<SaleFx> editSaleFxObjectProperty = new SimpleObjectProperty<>(new SaleFx());
    private ObservableList<SaleFx> saleFxObservableList = FXCollections.observableArrayList();
    private List<SaleFx> saleFXList = new ArrayList<>();

    public void init() throws AppException {
        SaleDao saleDao = new SaleDao();
        List<Sale> saleList = saleDao.queryForAll(Sale.class);
        this.saleFXList.clear();
        saleList.forEach(sale -> {
            SaleFx saleFx = SaleConverter.convertToFx(sale);
            this.saleFXList.add(saleFx);
        });
        this.saleFxObservableList.setAll(saleFXList);
    }

    public void saveEditSaleInDatabase () throws AppException {
        saveOrUpdateData(this.getEditSaleFxObjectProperty());
    }

    public void saveSaleInDatabase () throws AppException {
        saveOrUpdateData(this.getSaleFxObjectProperty());
    }

    public void saveOrUpdateData(SaleFx editSaleFxObjectProperty) throws AppException {
        SaleDao saleDao = new SaleDao();
        Sale sale = SaleConverter.convertSaleFxToSale(editSaleFxObjectProperty);
        saleDao.createOrUpdate(sale);
        init();
    }

    public void deleteSalesInDataBase() throws AppException {
        SaleDao saleDao = new SaleDao();
        saleDao.deleteById(Sale.class, this.getEditSaleFxObjectProperty().getId());
        init();
    }

    public String getTotalPriceOfSale() throws SQLException, AppException {
        SaleDao saleDao = new SaleDao();
        String totalPriceString;
        double totalPrice = saleDao.getTotalPrice();
        totalPriceString = totalPrice+ " " + "zł";
        this.init();
        return totalPriceString;
    }

    public void finishSale(List<Sale> sales) throws AppException {
        SaleDao saleDao = new SaleDao();
        sales.forEach(e->{
            try {
                saleDao.deleteById(Sale.class, e.getId());
            } catch (AppException f) {
                f.printStackTrace();
            }
        });
        init();
    }

    public List<Sale> getSaleList() throws AppException {
        SaleDao saleDao = new SaleDao();
        return saleDao.queryForAll(Sale.class);
    }

    public String getListOfSoldItems() throws AppException {
        List<Sale> sales = this.getSaleList();
        StringBuilder stringBuilder = new StringBuilder();
        sales.forEach(e-> {
            stringBuilder.append(e.getBoughtItems()+"\n");
                }
        );
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }

    public String getValueOfSoldItems() throws AppException {
        List<Sale> sales = this.getSaleList();
        StringBuilder stringBuilder = new StringBuilder();
        sales.forEach(e-> {
                    stringBuilder.append(e.getNumberOfBoughtItems()+"\n");
                }
        );
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }

    public String getPriceOfSoldItems() throws AppException {
        List<Sale> sales = this.getSaleList();
        StringBuilder stringBuilder = new StringBuilder();
        sales.forEach(e-> stringBuilder.append(e.getTotalCostOfItem()+"\n")
        );
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }

    public SaleFx getSaleFxObjectProperty() {
        return saleFxObjectProperty.get();
    }

    public ObjectProperty<SaleFx> saleFxObjectPropertyProperty() {
        return saleFxObjectProperty;
    }

    public void setSaleFxObjectProperty(SaleFx saleFxObjectProperty) {
        this.saleFxObjectProperty.set(saleFxObjectProperty);
    }

    public SaleFx getEditSaleFxObjectProperty() {
        return editSaleFxObjectProperty.get();
    }

    public ObjectProperty<SaleFx> editSaleFxObjectPropertyProperty() {
        return editSaleFxObjectProperty;
    }

    public void setEditSaleFxObjectProperty(SaleFx editSaleFxObjectProperty) {
        this.editSaleFxObjectProperty.set(editSaleFxObjectProperty);
    }

    public ObservableList<SaleFx> getSaleFxObservableList() {
        return saleFxObservableList;
    }

    public void setSaleFxObservableList(ObservableList<SaleFx> saleFxObservableList) {
        this.saleFxObservableList = saleFxObservableList;
    }

    public List<SaleFx> getSaleFXList() {
        return saleFXList;
    }

    public void setSaleFXList(List<SaleFx> saleFXList) {
        this.saleFXList = saleFXList;
    }

}