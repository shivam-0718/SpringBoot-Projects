package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.entity.Customer;

import java.util.List;

public interface CustomerManagerService {
    String registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    String updateCustomer(Customer customer);
    String updateCustomer(Customer... properties);
    void deleteCustomer(Customer customer);

}
