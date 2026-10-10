package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.dto.CustomerRequest;
import in.shivamvyas.customermanager.dto.CustomerResponse;
import java.util.List;

public interface CustomerManagerService {
    CustomerResponse registerCustomer(CustomerRequest request);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse fetchCustomer(Long id);
    CustomerResponse updateCustomer(Long id, CustomerRequest request);
    String deleteCustomer(Long id);

}
