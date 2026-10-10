package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.dto.CustomerRequest;
import in.shivamvyas.customermanager.dto.CustomerResponse;
import in.shivamvyas.customermanager.entity.Customer;
import in.shivamvyas.customermanager.exception.CustomerNotFoundException;
import in.shivamvyas.customermanager.repository.CustomerManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerManagerService{
    private CustomerManagerRepo customerRepo;

    @Autowired
    public void setCustomerRepo(CustomerManagerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerResponse registerCustomer(CustomerRequest request) {
        Customer customer = toEntity(request); // mapping request DTO to Customer entity
        Customer registeredCustomer = customerRepo.save(customer); // saving Customer to database
        return toResponse(registeredCustomer); // mapping entity to response DTO and returning the same
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customersList = customerRepo.findAll(); // fetch all Customers from DB
        List<CustomerResponse> customers = new ArrayList<>();

        for(Customer customer : customersList) {
            CustomerResponse response = toResponse(customer); // mapping Customer to CustomerResponse DTO and saving it in List
            customers.add(response);
        }

        return customers;
    }

    @Override
    public CustomerResponse fetchCustomer(Long id) {
        Optional<Customer> optional = customerRepo.findById(id);
        if(optional.isPresent()) {
            Customer customer = optional.get();
            return toResponse(customer); // mapping Customer to CustomerResponse DTO
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Optional<Customer> optional = customerRepo.findById(id);
        if(optional.isPresent()) {
            Customer customer = optional.get();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setCity(request.getCity());
            Customer updatedCustomer = customerRepo.save(customer);
            return toResponse(updatedCustomer);
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }

    @Override
    public String deleteCustomer(Long id) {
        Optional<Customer> optional = customerRepo.findById(id);
        if(optional.isPresent()) {
            customerRepo.delete(optional.get());
            return "The customer details with the given id " + optional.get().getId() + " has been deleted";
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }

    // Private Helper method to map the CustomerRequest DTO details
    // to Customer Entity, thereby storing Entity in DB
    private Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .city(request.getCity())
                .build();
    }

    // Private Helper method to map the DB fetched Customer object details
    // to CustomerResponse DTO
    private CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .city(customer.getCity())
                .build();
    }
}
