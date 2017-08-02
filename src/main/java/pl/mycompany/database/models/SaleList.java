package pl.mycompany.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "sale_list")
public class SaleList implements BaseModel{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "date_of_sale")
    private Date dateOfSale;
    @DatabaseField(columnName = "customer")
    private String customer;
    @DatabaseField(columnName = "item_list")
    private String item_list;
    @DatabaseField(columnName = "value_of_items")
    private String valueOfItems;
    @DatabaseField(columnName = "total_cost_of_items")
    private String totalCostOfItem;
    @DatabaseField(columnName = "total_price")
    private String totalPrice;

    public SaleList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItem_list() {
        return item_list;
    }

    public void setItem_list(String item_list) {
        this.item_list = item_list;
    }

    public String getValueOfItems() {
        return valueOfItems;
    }

    public void setValueOfItems(String valueOfItems) {
        this.valueOfItems = valueOfItems;
    }

    public String getTotalCostOfItem() {
        return totalCostOfItem;
    }

    public void setTotalCostOfItem(String totalCostOfItem) {
        this.totalCostOfItem = totalCostOfItem;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}