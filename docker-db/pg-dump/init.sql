-- Connect to the default database (this is PostgreSQL-specific)
-- You will need to run this manually or ensure your script is executed in the right context

-- Create the database if it does not exist
CREATE DATABASE product_service_db;

-- Connect to the newly created database
\c product_service_db;

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS product_schema;

-- Switch to the newly created schema
SET search_path TO product_schema;

-- Create Customers table
CREATE TABLE IF NOT EXISTS Customers (
                                         customer_id SERIAL PRIMARY KEY,
                                         first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    phone_number VARCHAR(15)
    );

-- Create Products table
CREATE TABLE IF NOT EXISTS Products (
                                        product_id SERIAL PRIMARY KEY,
                                        product_name VARCHAR(100),
    price NUMERIC(10, 2),
    stock_quantity INT
    );

-- Create Orders table with foreign key to Customers
CREATE TABLE IF NOT EXISTS Orders (
                                      order_id SERIAL PRIMARY KEY,
                                      customer_id INT REFERENCES Customers(customer_id) ON DELETE CASCADE,
    order_date DATE,
    total_amount NUMERIC(10, 2)
    );

-- Create OrderItems table with foreign keys to Orders and Products
CREATE TABLE IF NOT EXISTS OrderItems (
                                          order_item_id SERIAL PRIMARY KEY,
                                          order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    product_id INT REFERENCES Products(product_id),
    quantity INT,
    price NUMERIC(10, 2)
    );

-- Create Payments table with foreign key to Orders
CREATE TABLE IF NOT EXISTS Payments (
                                        payment_id SERIAL PRIMARY KEY,
                                        order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    payment_date DATE,
    amount NUMERIC(10, 2),
    payment_method VARCHAR(50)
    );

-- Create Shipping table with foreign key to Orders
CREATE TABLE IF NOT EXISTS Shipping (
                                        shipping_id SERIAL PRIMARY KEY,
                                        order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    shipping_address VARCHAR(255),
    shipping_date DATE,
    delivery_date DATE
    );

-- Insert sample data into Customers
INSERT INTO Customers (first_name, last_name, email, phone_number)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '123-456-7890'),
    ('Jane', 'Smith', 'jane.smith@example.com', '098-765-4321');

-- Insert sample data into Products
INSERT INTO Products (product_name, price, stock_quantity)
VALUES
    ('Laptop', 1200.50, 50),
    ('Smartphone', 799.99, 100),
    ('Tablet', 499.00, 75);

-- Insert sample data into Orders
INSERT INTO Orders (customer_id, order_date, total_amount)
VALUES
    (1, '2024-09-25', 1999.99),
    (2, '2024-09-26', 999.00);

-- Insert sample data into OrderItems
INSERT INTO OrderItems (order_id, product_id, quantity, price)
VALUES
    (1, 1, 1, 1200.50), -- 1 Laptop
    (1, 2, 1, 799.99),  -- 1 Smartphone
    (2, 3, 2, 499.00);  -- 2 Tablets

-- Insert sample data into Payments
INSERT INTO Payments (order_id, payment_date, amount, payment_method)
VALUES
    (1, '2024-09-25', 1999.99, 'Credit Card'),
    (2, '2024-09-26', 999.00, 'PayPal');

-- Insert sample data into Shipping
INSERT INTO Shipping (order_id, shipping_address, shipping_date, delivery_date)
VALUES
    (1, '123 Main St, Cityville, State, 12345', '2024-09-26', '2024-09-30'),
    (2, '456 Oak St, Townsville, State, 67890', '2024-09-27', '2024-10-02');
