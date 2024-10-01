package org.product.restful.api;

import org.info.product.models.Customer;
import org.product.info.services.CustomerService;
import org.product.restful.api.controller.CustomerController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerService customerService = context.getBean(CustomerService.class);

        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            System.out.println(customer.getCustomerId() + " " + customer.getFirstName() + " " + customer.getLastName());
        }

    }

}
