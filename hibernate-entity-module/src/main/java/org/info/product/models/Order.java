package org.info.product.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Orders", schema = "product_schema")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @Column(name = "order_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "total_amount", nullable = true)
    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Payment> payments;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Shipping> shippings;

    public Order(int orderId, Customer customer, Date orderDate, double totalAmount, Set<OrderItem> orderItems, Set<Payment> payments, Set<Shipping> shippings) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.payments = payments;
        this.shippings = shippings;
    }

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Shipping> getShippings() {
        return shippings;
    }

    public void setShippings(Set<Shipping> shippings) {
        this.shippings = shippings;
    }
}
