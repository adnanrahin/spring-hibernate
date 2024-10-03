package org.product.info.services;

import org.info.product.models.Order;
import org.info.product.models.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAllOrderItem();

    OrderItem findOrderItemById(long id);

    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(OrderItem orderItem);

    void deleteOrderItemById(long id);

}
