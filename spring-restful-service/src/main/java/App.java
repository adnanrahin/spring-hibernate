import org.info.product.models.Customer;
import org.product.info.services.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");

        CustomerService customerService = context.getBean(CustomerService.class);

        List<Customer> customers = customerService.findAllCustomer();
        for (Customer customer : customers) {
            System.out.println(customer.getCustomerId() + " " + customer.getFirstName() + " " + customer.getLastName());
        }

    }

}
