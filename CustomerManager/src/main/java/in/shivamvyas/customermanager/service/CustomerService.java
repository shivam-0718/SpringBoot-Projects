package in.shivamvyas.customermanager.service;

import in.shivamvyas.customermanager.entity.Customer;
import in.shivamvyas.customermanager.exception.CustomerNotFoundException;
import in.shivamvyas.customermanager.repository.CustomerManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public String registerCustomer(Customer customer) {
        customerRepo.save(customer);
        return "The customer has been registed with id number " + customer.getId();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer fetchCustomer(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if(customer.isPresent()) {
            return customer.get();
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }

    @Override
    public String updateCustomer(Customer customer) {
        Optional<Customer> optional = customerRepo.findById(customer.getId());
        if(optional.isPresent()) {
            customerRepo.save(customer);
            return "The customer details with the given id " + customer.getId() + " has been updated";
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }

    @Override
    public String deleteCustomer(Customer customer) {
        Optional<Customer> optional = customerRepo.findById(customer.getId());
        if(optional.isPresent()) {
            customerRepo.delete(customer);
            return "The customer details with the given id " + customer.getId() + " has been deleted";
        }
        throw new CustomerNotFoundException("The customer with the given id is not present. Try again with the correct id");
    }
}
