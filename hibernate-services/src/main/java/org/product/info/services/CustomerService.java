package org.product.info.services;

import org.info.product.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    Customer save(Customer customer);

    void delete(Customer customer);

    void deleteById(Long id);

}
