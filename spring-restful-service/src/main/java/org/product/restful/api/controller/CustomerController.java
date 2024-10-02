package org.product.restful.api.controller;

import org.product.info.services.CustomerService;
import org.info.product.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Get a customer by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Create a new customer
    @PostMapping("/save")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // Update an existing customer
    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setCustomerId(id); // Assuming you have a setter for customerId
        Customer updatedCustomer = customerService.save(customer);
        if (updatedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // Delete a customer by ID
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
