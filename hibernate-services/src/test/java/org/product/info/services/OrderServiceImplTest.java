package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Customer;
import org.info.product.models.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

    private OrderService orderService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize SessionFactory from hibernate.cfg.xml
        sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

        // Initialize OrderService
        orderService = new OrderServiceImpl();

        // Inject SessionFactory into the OrderService
        ((OrderServiceImpl) orderService).setSessionFactory(sessionFactory);

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
    public void testSaveOrder() {
        // Create a customer
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        // Save customer in session (separate from order transaction)
        session.save(customer);
        transaction.commit(); // Commit to save customer before starting the order transaction
        transaction = session.beginTransaction();

        // Create and save order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(1500.00);

        orderService.save(order);

        // Retrieve and verify saved order
        Order savedOrder = orderService.findOrderById(order.getOrderId());
        assertNotNull(savedOrder);
        assertEquals(customer.getCustomerId(), savedOrder.getCustomer().getCustomerId());
        assertEquals(1500.00, savedOrder.getTotalAmount());

        // Clean up
        orderService.delete(savedOrder);
    }

    @Test
    public void testFindAllOrders() {
        // Create and save two customers
        Customer customer1 = new Customer();
        customer1.setFirstName("Alice");
        customer1.setLastName("Smith");
        customer1.setEmail("alice.smith@example.com");
        customer1.setPhoneNumber("1112223333");

        Customer customer2 = new Customer();
        customer2.setFirstName("Bob");
        customer2.setLastName("Brown");
        customer2.setEmail("bob.brown@example.com");
        customer2.setPhoneNumber("4445556666");

        session.save(customer1);
        session.save(customer2);
        transaction.commit(); // Commit customer transactions
        transaction = session.beginTransaction(); // Begin new transaction for orders

        // Create and save two orders
        Order order1 = new Order();
        order1.setCustomer(customer1);
        order1.setOrderDate(new Date());
        order1.setTotalAmount(300.00);
        orderService.save(order1);

        Order order2 = new Order();
        order2.setCustomer(customer2);
        order2.setOrderDate(new Date());
        order2.setTotalAmount(500.00);
        orderService.save(order2);

        // Fetch all orders and validate
        List<Order> orders = orderService.findAllOrders();
        assertNotNull(orders);
        assertTrue(orders.size() >= 2);

        // Clean up
        orderService.delete(order1);
        orderService.delete(order2);
    }
}
