package org.info.product.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Payments", schema = "product_schema")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Column(name = "payment_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "amount", nullable = true)
    private double amount;

    @Column(name = "payment_method", length = 50, nullable = true)
    private String paymentMethod;

    public Payment(int paymentId, Order order, Date paymentDate, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Payment() {

    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
