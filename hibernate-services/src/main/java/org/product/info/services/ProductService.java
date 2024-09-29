package org.product.info.services;

import org.info.product.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    void delete(Product product);

    void deleteById(Long id);

}
