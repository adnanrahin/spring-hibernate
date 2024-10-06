package org.product.crud.app.implemnetations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.product.crud.app.services.CustomerService;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceImplTest {

    private CustomerService customerService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;


    @Before
    public void setUp() throws Exception {
        sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        customerService = new CustomerServiceImpl(sessionFactory);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
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
    public void findAll() {
        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPhoneNumber("1234567890");

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setEmail("jane.doe@example.com");
        customer2.setPhoneNumber("9876543210");

        customerService.save(customer1);
        customerService.save(customer2);

        List<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertTrue(customers.size() >= 2);

        customerService.delete(customer1);
        customerService.delete(customer2);
    }

    @Test
    public void findById() {
        Customer customer = new Customer();
        customer.setFirstName("Alice");
        customer.setLastName("Smith");
        customer.setEmail("alice.smith@example.com");
        customer.setPhoneNumber("0987654321");

        // Save customer
        Customer savedCustomer = customerService.save(customer);

        // Find customer by ID
        Customer foundCustomer = customerService.findById(savedCustomer.getCustomerId());
        assertNotNull(foundCustomer);
        assertEquals("Alice", foundCustomer.getFirstName());
        assertEquals("Smith", foundCustomer.getLastName());

        // Clean up
        customerService.delete(savedCustomer);
    }

    @Test
    public void save() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        Customer savedCustomer = customerService.save(customer);
        assertNotNull(savedCustomer);
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());

        customerService.delete(savedCustomer);
    }

    @Test
    public void delete() {
        Customer customer = new Customer();
        customer.setFirstName("Michael");
        customer.setLastName("Jordan");
        customer.setEmail("michael.jordan@example.com");
        customer.setPhoneNumber("1111111111");

        // Save customer
        Customer savedCustomer = customerService.save(customer);

        // Delete customer
        customerService.delete(savedCustomer);

        // Verify deletion
        Customer deletedCustomer = customerService.findById(savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }

    @Test
    public void deleteById() {
        Customer customer = new Customer();
        customer.setFirstName("Bruce");
        customer.setLastName("Wayne");
        customer.setEmail("bruce.wayne@example.com");
        customer.setPhoneNumber("9999999999");

        Customer savedCustomer = customerService.save(customer);

        customerService.deleteById(savedCustomer.getCustomerId());

        Customer deletedCustomer = customerService.findById(savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }
}
