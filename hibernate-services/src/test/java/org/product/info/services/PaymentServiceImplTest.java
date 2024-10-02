package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Order;
import org.info.product.models.Payment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    private PaymentService paymentService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize SessionFactory from hibernate.cfg.xml
        sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

        // Initialize PaymentService
        paymentService = new PaymentServiceImpl();

        // Inject SessionFactory into PaymentService
        ((PaymentServiceImpl) paymentService).setSessionFactory(sessionFactory);

        // Open a new session and start a transaction
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void findAllPayments() {
        // Setup: Create and save some payments
        Order order = new Order();
        order.setOrderDate(new Date());
        session.save(order);

        transaction.commit();
        transaction = session.beginTransaction();

        Payment payment1 = new Payment();
        payment1.setOrder(order);
        payment1.setPaymentDate(new Date());
        payment1.setAmount(500.00);
        payment1.setPaymentMethod("Credit Card");

        Payment payment2 = new Payment();
        payment2.setOrder(order);
        payment2.setPaymentDate(new Date());
        payment2.setAmount(1000.00);
        payment2.setPaymentMethod("PayPal");

        paymentService.save(payment1);
        paymentService.save(payment2);

        // Find all payments
        List<Payment> payments = paymentService.findAllPayments();

        assertNotNull(payments);
        assertTrue(payments.size() >= 2);

        // Clean up: Delete payments after test
        paymentService.delete(payment1);
        paymentService.delete(payment2);
    }

    @Test
    void findPaymentById() {
        // Setup: Create and save a payment
        Order order = new Order();
        order.setOrderDate(new Date());
        session.save(order);

        transaction.commit();
        transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setAmount(200.00);
        payment.setPaymentMethod("Credit Card");

        Payment savedPayment = paymentService.save(payment);

        // Act: Find the payment by its ID
        Payment foundPayment = paymentService.findPaymentById(savedPayment.getPaymentId());

        // Assert: Ensure the payment was found correctly
        assertNotNull(foundPayment);
        assertEquals(savedPayment.getPaymentId(), foundPayment.getPaymentId());
        assertEquals(200.00, foundPayment.getAmount());

        // Clean up: Delete payment after test
        paymentService.delete(foundPayment);
    }

    @Test
    void save() {
        // Setup: Create and save a payment
        Order order = new Order();
        order.setOrderDate(new Date());
        session.save(order);

        transaction.commit();
        transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setAmount(150.00);
        payment.setPaymentMethod("Debit Card");

        // Act: Save the payment
        Payment savedPayment = paymentService.save(payment);

        // Assert: Ensure the payment was saved and contains a valid ID
        assertNotNull(savedPayment);
        assertNotNull(savedPayment.getPaymentId());

        // Clean up: Delete payment after test
        paymentService.delete(savedPayment);
    }

    @Test
    void delete() {
        // Setup: Create and save a payment
        Order order = new Order();
        order.setOrderDate(new Date());
        session.save(order);

        transaction.commit();
        transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setAmount(300.00);
        payment.setPaymentMethod("Bank Transfer");

        Payment savedPayment = paymentService.save(payment);

        // Act: Delete the payment
        paymentService.delete(savedPayment);

        // Assert: Ensure the payment is deleted
        Payment foundPayment = paymentService.findPaymentById(savedPayment.getPaymentId());
        assertNull(foundPayment);
    }

    @Test
    void deletePaymentById() {
        // Setup: Create and save a payment
        Order order = new Order();
        order.setOrderDate(new Date());
        session.save(order);

        transaction.commit();
        transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setAmount(400.00);
        payment.setPaymentMethod("Cash");

        Payment savedPayment = paymentService.save(payment);

        // Act: Delete the payment by ID
        paymentService.deletePaymentById(savedPayment.getPaymentId());

        // Assert: Ensure the payment is deleted
        Payment foundPayment = paymentService.findPaymentById(savedPayment.getPaymentId());
        assertNull(foundPayment);
    }
}
