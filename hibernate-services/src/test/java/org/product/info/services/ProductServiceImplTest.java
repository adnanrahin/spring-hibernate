package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.info.product.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ProductServiceImplTest {

    private ProductService productService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize SessionFactory from hibernate.cfg.xml
        sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

        // Initialize ProductService
        productService = new ProductServiceImpl();

        // Inject SessionFactory into ProductService
        ((ProductServiceImpl) productService).setSessionFactory(sessionFactory);

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
    public void testSaveProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(99.99);
        product.setStockQuantity(10);

        // Save the product
        productService.save(product);

        transaction.commit();
        transaction = session.beginTransaction();

        // Fetch the saved product
        Product savedProduct = productService.findProductById(product.getProductId());
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getProductName());
        assertEquals(99.99, savedProduct.getPrice());
        assertEquals(10, savedProduct.getStockQuantity());

        // Cleanup
        productService.delete(savedProduct);
    }

    @Test
    public void testFindAllProducts() {
        Product product1 = new Product();
        product1.setProductName("Product One");
        product1.setPrice(50.00);
        product1.setStockQuantity(20);
        productService.save(product1);

        Product product2 = new Product();
        product2.setProductName("Product Two");
        product2.setPrice(150.00);
        product2.setStockQuantity(30);
        productService.save(product2);

        transaction.commit();
        transaction = session.beginTransaction();

        List<Product> products = productService.findAllProduct();
        assertNotNull(products);
        assertTrue(products.size() >= 2);

        // Cleanup
        productService.delete(product1);
        productService.delete(product2);
    }

    @Test
    public void testFindProductById() {
        Product product = new Product();
        product.setProductName("Find Me");
        product.setPrice(75.00);
        product.setStockQuantity(15);

        productService.save(product);

        Product foundProduct = productService.findProductById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals("Find Me", foundProduct.getProductName());

        // Cleanup
        productService.delete(foundProduct);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Product to Delete");
        product.setPrice(120.00);
        product.setStockQuantity(5);

        productService.save(product);

        productService.delete(product);

        Product deletedProduct = productService.findProductById(product.getProductId());
        assertNull(deletedProduct);
    }

    @Test
    public void testDeleteById() {
        Product product = new Product();
        product.setProductName("Delete Me By ID");
        product.setPrice(90.00);
        product.setStockQuantity(7);

        productService.save(product);

        productService.deleteProductById(product.getProductId());

        Product deletedProduct = productService.findProductById(product.getProductId());
        assertNull(deletedProduct);
    }
}
