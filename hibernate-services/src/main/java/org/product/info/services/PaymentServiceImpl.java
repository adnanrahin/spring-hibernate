package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Payment> findAllPayments() {
        Session session = sessionFactory.openSession();
        List<Payment> payments = null;
        try {
            payments = session.createQuery("from Payment", Payment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return payments;
    }

    @Override
    public Payment findPaymentById(long id) {
        Session session = sessionFactory.openSession();
        Payment payment = null;
        try {
            payment = session.get(Payment.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return payment;
    }

    @Override
    public Payment save(Payment payment) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (payment.getPaymentId() == null) {
                session.save(payment);  // Save new payment
            } else {
                session.update(payment);  // Update existing payment
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return payment;
    }

    @Override
    public void delete(Payment payment) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deletePaymentById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, id);
            if (payment != null) {
                session.delete(payment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
