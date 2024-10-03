package org.product.info.services;

import org.info.product.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomer();

    Customer findCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    void deleteCustomerById(Long id);

}
