package pl.mycompany.database.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mycompany.database.dao.CustomerDao;
import pl.mycompany.modelfx.CustomerFx;
import pl.mycompany.utils.conventers.CustomerConverter;
import pl.mycompany.utils.exception.AppException;
import java.util.List;

public class CustomerLogic {

    private ObjectProperty<CustomerFx> customerFxObjectProperty = new SimpleObjectProperty<>(new CustomerFx());
    private ObjectProperty<CustomerFx> editCustomerFxObjectProperty = new SimpleObjectProperty<>(new CustomerFx());
    private ObservableList<CustomerFx> customerFxObservableList = FXCollections.observableArrayList();
    private ObservableList<String> stringObservableList = FXCollections.observableArrayList();

    public void init() throws AppException {
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customerList = customerDao.queryForAll(Customer.class);
        this.customerFxObservableList.clear();
        customerList.forEach(customer -> {
           CustomerFx customerFx = CustomerConverter.convertToFx(customer);
           this.customerFxObservableList.add(customerFx);
        });
        getNamesOfItem(customerList);
    }

    public void saveEditCustomerInDatabase () throws AppException {
        saveOrUpdateData(this.getEditCustomerFxObjectProperty());
    }

    public void saveCustomerInDatabase () throws AppException {
        saveOrUpdateData(this.getCustomerFxObjectProperty());
    }

    private void saveOrUpdateData(CustomerFx editCustomerFxObjectProperty) throws AppException {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = CustomerConverter.convertCustomerFxToCustomer(editCustomerFxObjectProperty);
        customerDao.createOrUpdate(customer);
        init();
    }

    public void deleteCustomerInDatabase() throws AppException {
        CustomerDao customerDao = new CustomerDao();
        customerDao.deleteById(Customer.class,this.getEditCustomerFxObjectProperty().getId());
        init();
    }

    public void getNamesOfItem(List<Customer> names) {
        this.stringObservableList.clear();
        names.forEach(e -> {
             CustomerFx customerFx = CustomerConverter.convertToFx(e);
            this.stringObservableList.add(customerFx.getFirstName()+ " " +customerFx.getLastName() + "\n"
            +customerFx.getAddress() + " " +customerFx.getNip());
        });
    }

    public CustomerFx getCustomerFxObjectProperty() {
        return customerFxObjectProperty.get();
    }

    public ObjectProperty<CustomerFx> customerFxObjectPropertyProperty() {
        return customerFxObjectProperty;
    }

    public void setCustomerFxObjectProperty(CustomerFx customerFxObjectProperty) {
        this.customerFxObjectProperty.set(customerFxObjectProperty);
    }

    public ObservableList<CustomerFx> getCustomerFxObservableList() {

        return customerFxObservableList;
    }

    public void setCustomerFxObservableList(ObservableList<CustomerFx> customerFxObservableList) {
        this.customerFxObservableList = customerFxObservableList;
    }

    public CustomerFx getEditCustomerFxObjectProperty() {
        return editCustomerFxObjectProperty.get();
    }

    public ObjectProperty<CustomerFx> editCustomerFxObjectPropertyProperty() {
        return editCustomerFxObjectProperty;
    }

    public void setEditCustomerFxObjectProperty(CustomerFx editCustomerFxObjectProperty) {
        this.editCustomerFxObjectProperty.set(editCustomerFxObjectProperty);
    }

    public ObservableList<String> getStringObservableList() {
        return stringObservableList;
    }

    public void setStringObservableList(ObservableList<String> stringObservableList) {
        this.stringObservableList = stringObservableList;
    }
}