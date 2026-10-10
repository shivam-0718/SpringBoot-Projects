package in.shivamvyas.customermanager.controller;

import in.shivamvyas.customermanager.dto.CustomerRequest;
import in.shivamvyas.customermanager.dto.CustomerResponse;
import in.shivamvyas.customermanager.service.CustomerManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerManagerController {
    private CustomerManagerService customerService;

    @Autowired
    public void setCustomerService(CustomerManagerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse registeredCustomer = customerService.registerCustomer(request);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> fetchACustomer(@PathVariable("id") Long id) {
        CustomerResponse customer = customerService.fetchCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody CustomerRequest request) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, request);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        String response= customerService.deleteCustomer(id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
