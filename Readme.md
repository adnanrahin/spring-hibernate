### Why this Project?

I built this project to dive deep into the core components of **Spring** and **Hibernate**, focusing on how their low-level APIs work behind the scenes. While many modern frameworks abstract away the complexities, understanding the foundation of these technologies allows for a better grasp of:

- **Spring's Dependency Injection (DI) and Aspect-Oriented Programming (AOP)** at a low level, offering deeper insights into how beans are managed and how cross-cutting concerns (like transactions) are handled.
- **Hibernate's ORM mechanisms**, like session management, lazy loading, and the intricacies of the Hibernate SessionFactory and Transaction APIs, to fully understand how the persistence layer interacts with the database.

Mastering these low-level APIs is crucial for developers who want to:
- Optimize performance by knowing exactly how and when resources like connections and sessions are being utilized.
- Debug issues more effectively, as you'll understand what's happening beneath higher-level abstractions.
- Customize behavior beyond the out-of-the-box solutions when unique requirements arise.

This project is a hands-on way for me to understand these concepts deeply, empowering me to make informed decisions when working on enterprise-level applications.

### Project Structure
```
.
├── docker-db
│   ├── docker-compose.yaml
│   └── pg-dump
│       └── init.sql
├── generic-crud-interface
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── org
│       │           └── product
│       │               └── crud
│       │                   ├── app
│       │                   │   ├── implemnetations
│       │                   │   │   └── CustomerServiceImpl.java
│       │                   │   └── services
│       │                   │       └── CustomerService.java
│       │                   └── generic
│       │                       ├── CrudDaoGenericServiceImpl.java
│       │                       └── CrudDaoGenericService.java
│       └── test
│           ├── java
│           │   └── org
│           │       └── product
│           │           ├── crud
│           │           │   └── app
│           │           │       └── implemnetations
│           │           │           └── CustomerServiceImplTest.java
│           │           └── info
│           │               └── AppTest.java
│           └── resources
│               └── hibernate.cfg.xml
├── hibernate-entity-module
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── org
│                   └── info
│                       └── product
│                           └── models
│                               ├── Customer.java
│                               ├── OrderItem.java
│                               ├── Order.java
│                               ├── Payment.java
│                               ├── Product.java
│                               └── Shipping.java
├── hibernate-services
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── org
│       │           └── product
│       │               └── info
│       │                   └── services
│       │                       ├── CustomerServiceImpl.java
│       │                       ├── CustomerService.java
│       │                       ├── OrderItemServiceImpl.java
│       │                       ├── OrderItemService.java
│       │                       ├── OrderServiceImpl.java
│       │                       ├── OrderService.java
│       │                       ├── PaymentServiceImpl.java
│       │                       ├── PaymentService.java
│       │                       ├── ProductServiceImpl.java
│       │                       ├── ProductService.java
│       │                       ├── ShippingServiceImpl.java
│       │                       └── ShippingService.java
│       └── test
│           ├── java
│           │   └── org
│           │       └── product
│           │           └── info
│           │               └── services
│           │                   ├── CustomerServiceImplTest.java
│           │                   ├── OrderServiceImplTest.java
│           │                   ├── PaymentServiceImplTest.java
│           │                   └── ProductServiceImplTest.java
│           └── resources
│               └── hibernate.cfg.xml
├── pom.xml
├── Readme.md
└── spring-restful-service
    ├── pom.xml
    └── src
        └── main
            ├── java
            │   ├── App.java
            │   └── org
            │       └── product
            │           └── restful
            │               └── api
            │                   ├── controller
            │                   │   ├── CustomerController.java
            │                   │   ├── OrderController.java
            │                   │   ├── PaymentController.java
            │                   │   └── ProductController.java
            │                   └── dto
            │                       ├── CustomerDTO.java
            │                       ├── OrderDTO.java
            │                       ├── PaymentDTO.java
            │                       └── ProductDTO.java
            └── webapp
                └── WEB-INF
                    ├── applicationContext.xml
                    └── web.xml

```

### Build The Project

1. This is a Multi-Module Maven Project, so it requires installing all the dependent modules before spinning up the RESTful web service.
2. Run `mvn clean install -DskipTests` to trigger the build without running the integration tests.
3. To run the build with integration tests, we need to build the Docker PostgresSQL database.
4. Navigate to the `docker-db` directory and run `docker-compose up -d` to start the PostgresSQL container.
5. Then, run `mvn clean install` again to complete the build process with integration tests.

### Deploy The Project

1. For deployment, I have used `wildfly-14.0.1.Final`.
2. You can download WildFly from [here](https://download.jboss.org/wildfly/14.0.1.Final/wildfly-14.0.1.Final.tar.gz).
3. Start the WildFly server, and deploy the application using the UI.

### Additional Deployment Instructions

After building the project and deploying the PostgreSQL database to the Docker container, follow these steps:

1. This Spring project has multiple RESTful WAR files that can be deployed to the WildFly server. You can deploy them in parallel.
    - First, we have `restful_service.war`, which uses its own `Service` and `ServiceImpl` interfaces. These interfaces are not highly generic, but they use the `hibernate-service` module to access all the APIs. The code for this module is under the `hibernate-service` source code.
    - Then, there is the `generic_restful_web_service`, which is a fully generic interface. All the Hibernate logic is implemented only once. The source code for this is available under the `generic-crud-interface` module.

Please make sure to deploy all the WAR files to the WildFly server after setting up the PostgresSQL database.

### How To make the API Calls
#### generic_restful_web_service

1. Get All Customer
```shell
curl --location 'http://localhost:8080/generic_restful_web_service/api/customers/getAll'
```
2. Save Customer
```shell
curl --location 'http://127.0.0.1:8080/restful_service/api/customers/save' \
--header 'Content-Type: application/json' \
--data-raw ' {
        "firstName": "Optimus",
        "lastName": "Prime",
        "email": "optimus.prime@example.com",
        "phoneNumber": "123-456-7890"
    }'
```
3. Delete Customer
```shell
curl --location --request DELETE 'http://127.0.0.1:8080/restful_service/api/customers/deleteById/127' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "123-456-7890",
    "orders": []
}'
```
