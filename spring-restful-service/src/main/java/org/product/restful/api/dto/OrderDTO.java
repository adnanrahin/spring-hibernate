package org.product.restful.api.dto;

import java.util.Date;
import java.util.Set;

public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private Date orderDate;
    private double totalAmount;
    private Set<Long> orderItemIds;
    private Set<Long> paymentIds;
    private Set<Long> shippingIds;

    public OrderDTO(Long orderId, Long customerId, Date orderDate, double totalAmount,
                    Set<Long> orderItemIds, Set<Long> paymentIds, Set<Long> shippingIds) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderItemIds = orderItemIds;
        this.paymentIds = paymentIds;
        this.shippingIds = shippingIds;
    }

    public OrderDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public Set<Long> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(Set<Long> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public Set<Long> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(Set<Long> paymentIds) {
        this.paymentIds = paymentIds;
    }

    public Set<Long> getShippingIds() {
        return shippingIds;
    }

    public void setShippingIds(Set<Long> shippingIds) {
        this.shippingIds = shippingIds;
    }
}
