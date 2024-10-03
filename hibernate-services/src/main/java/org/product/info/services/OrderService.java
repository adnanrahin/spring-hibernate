package org.product.info.services;

import org.info.product.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

    Order findOrderById(long id);

    Order save(Order order);

    void delete(Order order);

    void deleteOrderById(long id);

}
