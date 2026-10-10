package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.entity.Customer;

import java.util.List;

public interface CustomerManagerService {
    String registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer fetchCustomer(Long id);
    String updateCustomer(Customer customer);
    String deleteCustomer(Customer customer);

}
