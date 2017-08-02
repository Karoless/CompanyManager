package pl.mycompany.utils.conventers;

import pl.mycompany.database.models.Category;
import pl.mycompany.modelfx.CategoryFx;

public class CategoryConverter {

    public static CategoryFx convertToFX(Category category) {
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setCategoryName(category.getCategoryName());
        return categoryFx;
    }

    public static Category convertFxToCategory(CategoryFx categoryFx) {
        Category category = new Category();
        category.setId(categoryFx.getId());
        category.setCategoryName(categoryFx.getCategoryName());
        return category;
    }
}