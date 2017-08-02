package pl.mycompany.modelfx;

import javafx.beans.property.*;
import java.time.LocalDate;

public class SaleListFx {

    private ObjectProperty<LocalDate> dateOfSale = new SimpleObjectProperty(LocalDate.now());
    private StringProperty customer = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty item_list = new SimpleStringProperty();
    private StringProperty valueOfItems = new SimpleStringProperty();
    private StringProperty totalCostOfItem = new SimpleStringProperty();
    private StringProperty totalPrice = new SimpleStringProperty();

    public SaleListFx() {

    }

    public LocalDate getDateOfSale() {
        return dateOfSale.get();
    }

    public ObjectProperty<LocalDate> dateOfSaleProperty() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale.set(dateOfSale);
    }

    public String getCustomer() {
        return customer.get();
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getItem_list() {
        return item_list.get();
    }

    public StringProperty item_listProperty() {
        return item_list;
    }

    public void setItem_list(String item_list) {
        this.item_list.set(item_list);
    }

    public String getValueOfItems() {
        return valueOfItems.get();
    }

    public StringProperty valueOfItemsProperty() {
        return valueOfItems;
    }

    public void setValueOfItems(String valueOfItems) {
        this.valueOfItems.set(valueOfItems);
    }

    public String getTotalCostOfItem() {
        return totalCostOfItem.get();
    }

    public StringProperty totalCostOfItemProperty() {
        return totalCostOfItem;
    }

    public void setTotalCostOfItem(String totalCostOfItem) {
        this.totalCostOfItem.set(totalCostOfItem);
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }
}