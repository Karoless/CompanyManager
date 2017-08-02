package pl.mycompany.database.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mycompany.database.dao.CategoryDao;
import pl.mycompany.modelfx.CategoryFx;
import pl.mycompany.utils.conventers.CategoryConverter;
import pl.mycompany.utils.exception.AppException;
import java.sql.SQLException;
import java.util.List;

public class CategoryLogic {

    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>();

    public void saveInDatabase(String name) throws AppException {
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        category.setCategoryName(name);
        categoryDao.createOrUpdate(category);
        init();
    }

    public void init() throws AppException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.queryForAll(Category.class);
        initCategoryList(categories);
    }

    private void initCategoryList(List<Category> categories) {
        this.categoryList.clear();
        categories.forEach(e -> {
           CategoryFx categoryFx = CategoryConverter.convertToFX(e);
            this.categoryList.add(categoryFx);
        });
    }

    public void deleteCategory() throws AppException, SQLException {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteById(Category.class, category.getValue().getId());
        categoryDao.deleteByColumnName("CATEGORY_ID",this.category.getValue().getId());
        init();
    }
    public void updateCategory() throws AppException {
        CategoryDao categoryDao = new CategoryDao();
        Category temporaryCategory = categoryDao.findById(Category.class, getCategory().getId());
        temporaryCategory.setCategoryName(getCategory().getCategoryName());
        categoryDao.createOrUpdate(temporaryCategory);
        init();
    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList.sorted();
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }

}