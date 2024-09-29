package org.product.info.services;

import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.info.product.models.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.product.info.util.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplTest extends TestCase {

    private CustomerService customerService;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerServiceImpl();
        session = HibernateUtil.getSessionFactory().openSession();
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
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        Customer savedCustomer = customerService.save(customer);
        assertNotNull(savedCustomer);
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Alice");
        customer.setLastName("Smith");
        customer.setEmail("alice.smith@example.com");
        customer.setPhoneNumber("0987654321");

        Customer savedCustomer = customerService.save(customer);

        Customer foundCustomer = customerService.findById((long) savedCustomer.getCustomerId());
        assertNotNull(foundCustomer);
        assertEquals("Alice", foundCustomer.getFirstName());
        assertEquals("Smith", foundCustomer.getLastName());
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

        customerService.save(customer1);
        customerService.save(customer2);

        List<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertTrue(customers.size() >= 2);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Michael");
        customer.setLastName("Jordan");
        customer.setEmail("michael.jordan@example.com");
        customer.setPhoneNumber("1111111111");

        Customer savedCustomer = customerService.save(customer);

        customerService.delete(savedCustomer);

        Customer deletedCustomer = customerService.findById(savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }

    @Test
    public void testDeleteCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Bruce");
        customer.setLastName("Wayne");
        customer.setEmail("bruce.wayne@example.com");
        customer.setPhoneNumber("9999999999");

        Customer savedCustomer = customerService.save(customer);

        customerService.deleteById((long) savedCustomer.getCustomerId());

        Customer deletedCustomer = customerService.findById((long) savedCustomer.getCustomerId());
        assertNull(deletedCustomer);
    }
}