package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.info.product.models.Customer;
import org.info.product.models.Order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.product.info.util.HibernateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

    private OrderService orderService;
    private Session session;

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl();
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @AfterEach
    public void tearDown() {
        // Clean up data after each test
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Shipping").executeUpdate();
            session.createQuery("DELETE FROM Payment").executeUpdate();
            session.createQuery("DELETE FROM OrderItem").executeUpdate();
            session.createQuery("DELETE FROM Order").executeUpdate();
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

    @Test
    public void testSaveOrder() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        // Save the customer (you may want to save this in a separate service)
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(1500.00);

        orderService.saveOrder(order);

        Order savedOrder = orderService.findOrderById(order.getOrderId());
        assertNotNull(savedOrder);
        assertEquals(customer.getCustomerId(), savedOrder.getCustomer().getCustomerId());
        assertEquals(1500.00, savedOrder.getTotalAmount());
    }

    @Test
    public void testFindOrderById() {
        Customer customer = new Customer();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.com");
        customer.setPhoneNumber("0987654321");

        // Save the customer
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(1200.00);
        Order savedOrder = orderService.saveOrder(order);

        Order foundOrder = orderService.findOrderById(order.getOrderId());
        assertNotNull(foundOrder);
        assertEquals(order.getOrderId(), foundOrder.getOrderId());
    }

    @Test
    public void testFindAllOrders() {
        // Create and save two orders
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

        // Save the customers
        session.beginTransaction();
        session.save(customer1);
        session.save(customer2);
        session.getTransaction().commit();

        Order order1 = new Order();
        order1.setCustomer(customer1);
        order1.setOrderDate(new Date());
        order1.setTotalAmount(300.00);
        orderService.saveOrder(order1);

        Order order2 = new Order();
        order2.setCustomer(customer2);
        order2.setOrderDate(new Date());
        order2.setTotalAmount(500.00);
        orderService.saveOrder(order2);

        List<Order> orders = orderService.findAllOrders();
        assertNotNull(orders);
        assertTrue(orders.size() >= 2);
    }

    @Test
    public void testDeleteOrder() {
        Customer customer = new Customer();
        customer.setFirstName("David");
        customer.setLastName("Johnson");
        customer.setEmail("david.johnson@example.com");
        customer.setPhoneNumber("7778889999");

        // Save the customer
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(700.00);
        orderService.saveOrder(order);

        orderService.delete(order);

        Order deletedOrder = orderService.findOrderById(order.getOrderId());
        assertNull(deletedOrder);
    }

    @Test
    public void testDeleteOrderById() {
        Customer customer = new Customer();
        customer.setFirstName("Eva");
        customer.setLastName("Green");
        customer.setEmail("eva.green@example.com");
        customer.setPhoneNumber("0001112222");

        // Save the customer
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(850.00);
        orderService.saveOrder(order);

        orderService.deleteOrderById(order.getOrderId());

        Order deletedOrder = orderService.findOrderById(order.getOrderId());
        assertNull(deletedOrder);
    }
}
