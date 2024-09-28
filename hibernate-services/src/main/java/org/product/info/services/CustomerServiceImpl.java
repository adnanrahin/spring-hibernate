package org.product.info.services;

import org.info.product.models.Customer;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
