package org.product.info.services;

import org.info.product.models.Customer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.product.info.util.HibernateUtil;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Customer", Customer.class).list();
        }
    }

    @Override
    public Customer findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Customer.class, id);
        }
    }

    @Override
    public Customer save(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
            return customer;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
