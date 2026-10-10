package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.dto.CustomerResponse;
import in.shivamvyas.customermanager.entity.Customer;
import java.util.List;

public interface CustomerManagerService {
    CustomerResponse registerCustomer(Customer customer);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse fetchCustomer(Long id);
    CustomerResponse updateCustomer(Customer customer);
    String deleteCustomer(Customer customer);

}
