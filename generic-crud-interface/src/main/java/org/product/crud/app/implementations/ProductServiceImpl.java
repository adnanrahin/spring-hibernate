package org.product.crud.app.implementations;

import org.hibernate.SessionFactory;
import org.info.product.models.Product;
import org.product.crud.app.services.ProductService;
import org.product.crud.generic.CrudDaoGenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional
public class ProductServiceImpl extends CrudDaoGenericServiceImpl<Product, Long> implements ProductService {

    @Autowired
    public ProductServiceImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Product.class);
    }

}
