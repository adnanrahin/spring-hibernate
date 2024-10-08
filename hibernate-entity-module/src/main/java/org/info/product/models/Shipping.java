package org.info.product.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Shipping", schema = "product_schema")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long shippingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Column(name = "shipping_address", length = 255, nullable = true)
    private String shippingAddress;

    @Column(name = "shipping_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date shippingDate;

    @Column(name = "delivery_date")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;


    public Shipping(Long shippingId, Order order, String shippingAddress, Date shippingDate, Date deliveryDate) {
        this.shippingId = shippingId;
        this.order = order;
        this.shippingAddress = shippingAddress;
        this.shippingDate = shippingDate;
        this.deliveryDate = deliveryDate;
    }

    public Shipping() {

    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
