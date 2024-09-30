package org.product.restful.api.controller;

import org.product.info.services.CustomerService;
import org.info.product.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setCustomerId(id); // Assuming you have a setter for customerId
        return customerService.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
