package org.product.restful.api.controller;

import org.product.info.services.CustomerService;
import org.info.product.models.Customer;
import org.product.restful.api.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Convert Customer to CustomerDTO
    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }

    // Convert CustomerDTO to Customer
    private Customer convertToEntity(CustomerDTO customerDTO) {
        return new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                null // Assuming orders will be handled separately
        );
    }

    // Get all customers
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customersDTO = customerService.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customersDTO, HttpStatus.OK);
    }

    // Get a customer by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDTO(customer), HttpStatus.OK);
    }

    // Create a new customer
    @PostMapping("/save")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer = customerService.save(convertToEntity(customerDTO));
        return new ResponseEntity<>(convertToDTO(savedCustomer), HttpStatus.CREATED);
    }

    // Update an existing customer
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setCustomerId(id);
        Customer updatedCustomer = customerService.save(convertToEntity(customerDTO));
        if (updatedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDTO(updatedCustomer), HttpStatus.OK);
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
