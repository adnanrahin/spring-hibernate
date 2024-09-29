package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.info.product.models.Product;
import org.product.info.util.HibernateUtil;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> findAll() {
        List<Product> products;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            products = session.createQuery("FROM Product", Product.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            product = session.get(Product.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

        return product;
    }


    @Override
    public Product save(Product product) {
        Transaction transaction = null;
        Long productId;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            productId = (Long) session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

        return findById(productId);
    }

    @Override
    public void delete(Product product) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = findById(id);
        if (product != null) {
            delete(product);
        }
    }
}
