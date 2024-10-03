package org.product.info.services;

import org.info.product.models.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<OrderItem> findAllOrderItem() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from OrderItem", OrderItem.class).list();
        }
    }

    @Override
    public OrderItem findOrderItemById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(OrderItem.class, id);
        }
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(orderItem);
            transaction.commit();
            return orderItem;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(orderItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItemById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            OrderItem orderItem = session.get(OrderItem.class, id);
            if (orderItem != null) {
                session.delete(orderItem);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
