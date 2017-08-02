package pl.mycompany.utils.conventers;

import pl.mycompany.database.models.Customer;
import pl.mycompany.modelfx.CustomerFx;

public class CustomerConverter {

    public static Customer convertCustomerFxToCustomer(CustomerFx customerFx) {
        Customer customer = new Customer();
        customer.setId(customerFx.getId());
        customer.setFirstName(customerFx.getFirstName());
        customer.setLastName(customerFx.getLastName());
        customer.setAddress(customerFx.getAddress());
        customer.setNIP(customerFx.getNip());
        return customer;
    }

    public static CustomerFx convertToFx(Customer customer){
        CustomerFx customerFx = new CustomerFx();
        customerFx.setId(customer.getId());
        customerFx.setFirstName(customer.getFirstName());
        customerFx.setLastName(customer.getLastName());
        customerFx.setAddress(customer.getAddress());
        customerFx.setNip(customer.getNIP());
        return customerFx;
    }
}