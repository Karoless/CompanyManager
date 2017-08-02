package pl.mycompany.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sale")
public class Sale implements BaseModel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "bought_items", canBeNull = false)
    private String boughtItems;
    @DatabaseField(columnName = "number_of_items")
    private int numberOfBoughtItems;
    @DatabaseField(columnName = "total_cost_of_item")
    private double totalCostOfItem;
    @DatabaseField(columnName = "total_price")
    private String totalPrice;

    public Sale() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoughtItems() {
        return boughtItems;
    }

    public void setBoughtItems(String boughtItems) {
        this.boughtItems = boughtItems;
    }

    public int getNumberOfBoughtItems() {
        return numberOfBoughtItems;
    }

    public void setNumberOfBoughtItems(int numberOfBoughtItems) {
        this.numberOfBoughtItems = numberOfBoughtItems;
    }

    public double getTotalCostOfItem() {
        return totalCostOfItem;
    }

    public void setTotalCostOfItem(double totalCostOfItem) {
        this.totalCostOfItem = totalCostOfItem;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}