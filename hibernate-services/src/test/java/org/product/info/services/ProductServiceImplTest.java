package org.product.info.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.info.product.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.product.info.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ProductServiceImplTest {

    private ProductService productService;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl();
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
    public void testSaveProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(99.99);
        product.setStockQuantity(10);

        // Save the product
        productService.save(product);

        // Fetch the saved product
        Product savedProduct = productService.findById(product.getProductId());
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

        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertTrue(products.size() >= 2);

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

        Product foundProduct = productService.findById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals("Find Me", foundProduct.getProductName());

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

        Product deletedProduct = productService.findById(product.getProductId());
        assertNull(deletedProduct);
    }

    @Test
    public void testDeleteById() {
        Product product = new Product();
        product.setProductName("Delete Me By ID");
        product.setPrice(90.00);
        product.setStockQuantity(7);

        productService.save(product);

        productService.deleteById(product.getProductId());

        Product deletedProduct = productService.findById(product.getProductId());
        assertNull(deletedProduct);
    }
}
