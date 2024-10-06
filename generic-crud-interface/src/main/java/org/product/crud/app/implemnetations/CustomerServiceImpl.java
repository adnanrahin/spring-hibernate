package org.product.crud.app.implemnetations;

import org.hibernate.SessionFactory;
import org.info.product.models.Customer;
import org.product.crud.app.services.CustomerService;
import org.product.crud.generic.CrudDaoGenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl extends CrudDaoGenericServiceImpl<Customer, Long> implements CustomerService {

    @Autowired
    public CustomerServiceImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

}
