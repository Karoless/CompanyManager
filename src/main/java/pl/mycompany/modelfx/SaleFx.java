package pl.mycompany.modelfx;

import javafx.beans.property.*;

public class SaleFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty boughtItems = new SimpleStringProperty();
    private IntegerProperty numberOfBoughtItems = new SimpleIntegerProperty();
    private DoubleProperty totalPrice = new SimpleDoubleProperty();
    private StringProperty totalPriceOfShopping = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBoughtItems() {
        return boughtItems.get();
    }

    public StringProperty boughtItemsProperty() {
        return boughtItems;
    }

    public void setBoughtItems(String boughtItems) {
        this.boughtItems.set(boughtItems);
    }

    public int getNumberOfBoughtItems() {
        return numberOfBoughtItems.get();
    }

    public IntegerProperty numberOfBoughtItemsProperty() {
        return numberOfBoughtItems;
    }

    public void setNumberOfBoughtItems(int numberOfBoughtItems) {
        this.numberOfBoughtItems.set(numberOfBoughtItems);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getTotalPriceOfShopping() {
        return totalPriceOfShopping.get();
    }

    public StringProperty totalPriceOfShoppingProperty() {
        return totalPriceOfShopping;
    }

    public void setTotalPriceOfShopping(String totalPriceOfShopping) {
        this.totalPriceOfShopping.set(totalPriceOfShopping);
    }
}