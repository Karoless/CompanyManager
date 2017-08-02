package pl.mycompany.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import pl.mycompany.database.models.CategoryLogic;
import pl.mycompany.database.models.ItemLogic;
import pl.mycompany.modelfx.CategoryFx;
import pl.mycompany.utils.DialogsUtils;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.exception.AppException;
import java.sql.SQLException;

public class AddItemController {

    @FXML
    private Button addCategory;
    @FXML
    private TextField categoryTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<CategoryFx> categoryBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private Button addButton;

    private CategoryLogic categoryLogic;
    private ItemLogic itemLogic;

    @FXML
    public void initialize() {
        createLogic();
        bindings();
        initComboBox();
        disableButtonProperty();
        validation();
    }

    private void createLogic() {
        this.categoryLogic = new CategoryLogic();
        this.itemLogic = new ItemLogic();
        try {
            this.categoryLogic.init();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    private void validation() {
        FxmlUtils.doubleValidation(priceTextField);
        FxmlUtils.integerValidation(valueTextField);
    }

    @FXML
    private void disableButtonProperty() {
        this.deleteButton.disableProperty().bind(this.categoryLogic.categoryProperty().isNull());
        this.addCategory.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        this.editButton.disableProperty().bind(this.categoryLogic.categoryProperty().isNull());
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.categoryBox.valueProperty().isNull()));
    }

    @FXML
    private void initComboBox() {
        this.categoryComboBox.setItems(this.categoryLogic.getCategoryList());
        this.categoryBox.setItems(this.categoryLogic.getCategoryList());
    }

    @FXML
    public void bindings() {
        this.nameTextField.textProperty().bindBidirectional(this.itemLogic.getItemFxObjectProperty().nameProperty());
        this.categoryBox.valueProperty().bindBidirectional(this.itemLogic.getItemFxObjectProperty().categoryFxObjectPropertyProperty());
            Bindings.bindBidirectional(this.valueTextField.textProperty(), this.itemLogic.getItemFxObjectProperty().valueProperty(), new NumberStringConverter());
            Bindings.bindBidirectional(this.priceTextField.textProperty(), this.itemLogic.getItemFxObjectProperty().priceProperty(), new NumberStringConverter());
    }

    @FXML
    public void addCategory() {
        try {
            categoryLogic.saveInDatabase(categoryTextField.getText());
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        categoryTextField.clear();
    }

    @FXML
    public void deleteCategory() {
        try {
            if (DialogsUtils.showConfirmationDeleteDialog().get() == ButtonType.OK) {
                this.categoryLogic.deleteCategory();
            }
        } catch (AppException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void chooseComboBox() {
        this.categoryLogic.setCategory(this.categoryComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void editCategory() {
        String newCategoryName = DialogsUtils.editDialog(this.categoryLogic.getCategory().getCategoryName());
        if(newCategoryName!=null) {
            this.categoryLogic.getCategory().setCategoryName(newCategoryName);
            try {
                this.categoryLogic.updateCategory();
            } catch (AppException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }
    }

    @FXML
    public void addItem() {
        try {
            this.itemLogic.saveItemInDatabase();
        } catch (AppException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        clearFields();
    }

    @FXML
    private void clearFields() {
        this.nameTextField.clear();
        this.valueTextField.clear();
        this.priceTextField.clear();
        this.categoryBox.getSelectionModel().clearSelection();
    }

    public ItemLogic getItemLogic() {
        return itemLogic;
    }
}