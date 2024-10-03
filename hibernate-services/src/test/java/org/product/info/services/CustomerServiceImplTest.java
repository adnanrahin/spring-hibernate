package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplTest {

    private CustomerService customerService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize SessionFactory (directly load hibernate.cfg.xml using Configuration)
        sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

        // Create a new CustomerServiceImpl instance
        customerService = new CustomerServiceImpl();

        // Inject the sessionFactory into customerService
        ((CustomerServiceImpl) customerService).setSessionFactory(sessionFactory);

        // Start a session and transaction for each test
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
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        // Save customer
        Customer savedCustomer = customerService.saveCustomer(customer);
        assertNotNull(savedCustomer);
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());

        // Clean up
        customerService.deleteCustomer(savedCustomer);
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Alice");
        customer.setLastName("Smith");
        customer.setEmail("alice.smith@example.com");
        customer.setPhoneNumber("0987654321");

        // Save customer
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Find customer by ID
        Customer foundCustomer = customerService.findCustomerById(savedCustomer.getCustomerId());
        assertNotNull(foundCustomer);
        assertEquals("Alice", foundCustomer.getFirstName());
        assertEquals("Smith", foundCustomer.getLastName());

        // Clean up
        customerService.deleteCustomer(savedCustomer);
    }

    @Test
    public void testFindAllCustomers() {
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

        // Save customers
        customerService.saveCustomer(customer1);
        customerService.saveCustomer(customer2);

        // Retrieve all customers
        List<Customer> customers = customerService.findAllCustomer();
        assertNotNull(customers);
        assertTrue(customers.size() >= 2);

        // Clean up
        customerService.deleteCustomer(customer1);
        customerService.deleteCustomer(customer2);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Michael");
        customer.setLastName("Jordan");
        customer.setEmail("michael.jordan@example.com");
        customer.setPhoneNumber("1111111111");

        // Save customer
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Delete customer
        customerService.deleteCustomer(savedCustomer);

        // Verify deletion
        Customer deletedCustomer = customerService.findCustomerById(savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }

    @Test
    public void testDeleteCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Bruce");
        customer.setLastName("Wayne");
        customer.setEmail("bruce.wayne@example.com");
        customer.setPhoneNumber("9999999999");

        // Save customer
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Delete customer by ID
        customerService.deleteCustomerById(savedCustomer.getCustomerId());

        // Verify deletion
        Customer deletedCustomer = customerService.findCustomerById(savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }
}
