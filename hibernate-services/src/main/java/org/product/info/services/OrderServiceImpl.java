package org.product.info.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Order> findAllOrders() {
        try (Session session = sessionFactory.openSession()) {
            List<Order> orders = session.createQuery("FROM Order", Order.class).list();
            for (Order order : orders) {
                Hibernate.initialize(order.getOrderItems());
                Hibernate.initialize(order.getPayments());
                Hibernate.initialize(order.getShippings());
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return an empty list if an error occurs
        }
    }

    @Override
    public Order findOrderById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Order order = session.get(Order.class, id);
            if (order != null) {
                Hibernate.initialize(order.getOrderItems());
                Hibernate.initialize(order.getPayments());
                Hibernate.initialize(order.getShippings());
            }
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    @Override
    public Order save(Order order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Order order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if any exception occurs
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                session.delete(order);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if any exception occurs
            }
            e.printStackTrace();
        }
    }
}
