package org.product.info;

import org.info.product.models.Customer;
import org.product.crud.app.services.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerService customerService = context.getBean(CustomerService.class);

        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            System.out.println(customer.getCustomerId() + " " + customer.getFirstName() + " " + customer.getLastName());
        }

    }

}
