package pl.mycompany.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "items")
public class Item implements BaseModel {

    @DatabaseField(columnName = "category_id", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private Category category;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name_of_item", unique = true, canBeNull = false)
    private String nameOfItem;
    @DatabaseField(columnName = "price", canBeNull = false)
    private double price;
    @DatabaseField(columnName = "numbers_of_item")
    private int numberOfItems;

    public Item () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamOfItem() {
        return nameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfItem = nameOfItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}