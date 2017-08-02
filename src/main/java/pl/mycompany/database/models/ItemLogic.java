package pl.mycompany.database.models;

import javafx.beans.property.ObjectProperty;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mycompany.database.dao.CategoryDao;
import pl.mycompany.database.dao.ItemDao;
import pl.mycompany.modelfx.CategoryFx;
import pl.mycompany.modelfx.ItemFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.conventers.CategoryConverter;
import pl.mycompany.utils.conventers.ItemConverter;
import pl.mycompany.utils.exception.AppException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemLogic {

    private ObjectProperty<ItemFx> itemFxObjectProperty = new SimpleObjectProperty<>(new ItemFx());
    private ObservableList<ItemFx> itemFxObservableList = FXCollections.observableArrayList();
    private ObjectProperty<ItemFx> editItemFxObjectProperty = new SimpleObjectProperty<>(new ItemFx());
    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();
    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();
    private ObservableList<ItemFx>  itemFxNameObservableList = FXCollections.observableArrayList();
    private ObservableList<String> stringObservableList = FXCollections.observableArrayList();
    private List<ItemFx> itemFxList = new ArrayList<>();

    public void init() throws AppException {
        List<Item> itemList = getItemList();
        this.itemFxList.clear();

        itemList.forEach(item -> {
            ItemFx itemFx = ItemConverter.convertToFx(item);
            this.itemFxList.add(itemFx);
        });
        this.itemFxObservableList.setAll(itemFxList);

        initCategoryList();
        getNamesOfItem(itemList);
    }

    public List<Item> getItemList() throws AppException {
        ItemDao itemDao = new ItemDao();
        return itemDao.queryForAll(Item.class);
    }

    public void saveEditItemInDatabase () throws AppException {
        saveOrUpdateData(this.getEditItemFxObjectProperty());
    }

    public void saveItemInDatabase() throws AppException {
        saveOrUpdateData(this.getItemFxObjectProperty());
    }

    public void saveOrUpdateData(ItemFx editCustomerFxObjectProperty) throws AppException {
        ItemDao itemDao = new ItemDao();
        Item item = ItemConverter.convertItemFxToItem(editCustomerFxObjectProperty);
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, editCustomerFxObjectProperty.getCategoryFxObjectProperty().getId());
        item.setCategory(category);
        itemDao.createOrUpdate(item);
        init();
    }
    public void saveItem(Item item) throws AppException {
        ItemDao itemDao = new ItemDao();
        itemDao.createOrUpdate(item);
    }

    public void deleteItemInDataBase() throws AppException {
        ItemDao itemDao =new ItemDao();
        itemDao.deleteById(Item.class, this.getEditItemFxObjectProperty().getId());
        init();
    }

    private void initCategoryList() throws AppException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.queryForAll(Category.class);
        this.categoryList.clear();
        categories.forEach(e -> {
            CategoryFx categoryFx = CategoryConverter.convertToFX(e);
            this.categoryList.add(categoryFx);
        });
    }

    private Predicate<ItemFx> predicateCategory() {
        return  itemFx -> itemFx.getCategoryFxObjectProperty().getId() ==getCategoryFxObjectProperty().getId();
    }

    public void filterByCategory() {
        if(getCategoryFxObjectProperty() != null) {
            filterPredicate(predicateCategory());
        }
        else
        {  this.itemFxObservableList.setAll(this.itemFxList);
            try {
                this.init();
            } catch (AppException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }
    }

    private  void filterPredicate(Predicate<ItemFx> predicate) {
        List<ItemFx> newList = itemFxList.stream().filter(predicate).collect(Collectors.toList());
        this.itemFxObservableList.setAll(newList);
        List<Item> itemList = new ArrayList<>();
        newList.forEach(itemFx -> {
            Item item = ItemConverter.convertItemFxToItem(itemFx);
            itemList.add(item);
        });
        this.getNamesOfItem(itemList);
    }

    public void getNamesOfItem(List<Item> names) {
        this.stringObservableList.clear();
        names.forEach(e -> {
            this.stringObservableList.add(e.getNamOfItem());
        });
    }

    public double checkPriceOfItem(String name, List<Item> names) {
        final double[] price = {0};
        names.forEach(e -> {
            if(name.equals(e.getNamOfItem())) {
                price[0] = e.getPrice();
            }
        });
        return price[0];
    }

    public int checkItemAvailability(String name, List<Item> names) {
        final int[] itemAvailability = {0};
        names.forEach(e -> {
            if(name.equals(e.getNamOfItem())) {
                itemAvailability[0] = e.getNumberOfItems();
            }
        });
        return itemAvailability[0];
    }

    public boolean checkAvailabilityAndUpdateData(String name, List<Item> names, int value) {
        int a = checkItemAvailability(name, names);
        boolean result = false;
        if(a>=value)
        {   names.forEach(e-> {
            if(name.equals(e.getNamOfItem())) {
                e.setNumberOfItems(a-value);
                try {
                    this.saveItem(e);
                } catch (AppException e1) {
                    DialogsUtils.errorDialog(e1.getMessage());
                }
            }
        });
            result=true;
        }
        else {
            DialogsUtils.errorDialog(FxmlUtils.getResourceBundle().getString("error.noItem"));
        }
        return result;
    }
    public void updateAfterRemoverItemFromSale(String name, List<Item> names, int value) {
        names.forEach(e-> {
            if(name.equals(e.getNamOfItem())) {
                e.setNumberOfItems(value+e.getNumberOfItems());
                try {
                    this.saveItem(e);
                } catch (AppException e1) {
                    DialogsUtils.errorDialog(e1.getMessage());
                }
            }
        });

    }

    public ItemFx getItemFxObjectProperty() {
        return itemFxObjectProperty.get();
    }

    public ObjectProperty<ItemFx> itemFxObjectPropertyProperty() {
        return itemFxObjectProperty;
    }

    public void setItemFxObjectProperty(ItemFx itemFxObjectProperty) {
        this.itemFxObjectProperty.set(itemFxObjectProperty);
    }

    public ObservableList<ItemFx> getItemFxObservableList() {
        return itemFxObservableList;
    }

    public void setItemFxObservableList(ObservableList<ItemFx> itemFxObservableList) {
        this.itemFxObservableList = itemFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }

    public ItemFx getEditItemFxObjectProperty() {
        return editItemFxObjectProperty.get();
    }

    public ObjectProperty<ItemFx> editItemFxObjectPropertyProperty() {
        return editItemFxObjectProperty;
    }

    public void setEditItemFxObjectProperty(ItemFx editItemFxObjectProperty) {
        this.editItemFxObjectProperty.set(editItemFxObjectProperty);
    }

    public ObservableList<ItemFx> getItemFxNameObservableList() {
        return itemFxNameObservableList.sorted();
    }

    public void setItemFxNameObservableList(ObservableList<ItemFx> itemFxNameObservableList) {
        this.itemFxNameObservableList = itemFxNameObservableList;
    }

    public ObservableList<String> getStringObservableList() {
        return stringObservableList;
    }

    public void setStringObservableList(ObservableList<String> stringObservableList) {
        this.stringObservableList = stringObservableList;
    }

    public List<ItemFx> getItemFxList() {
        return itemFxList;
    }

    public void setItemFxList(List<ItemFx> itemFxList) {
        this.itemFxList = itemFxList;
    }
}