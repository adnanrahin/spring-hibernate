package org.product.restful.api.controller;

import org.info.product.models.*;
import org.product.restful.api.dto.OrderDTO;
import org.product.info.services.OrderService;
import org.product.info.services.CustomerService;
import org.product.info.services.OrderItemService;
import org.product.info.services.PaymentService;
import org.product.info.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;

    @Autowired
    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           OrderItemService orderItemService,
                           PaymentService paymentService,
                           ShippingService shippingService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
        this.shippingService = shippingService;
    }

    private OrderDTO convertToDTO(Order order) {
        Set<Long> orderItemIds = order.getOrderItems().stream().map(OrderItem::getOrderItemId).collect(Collectors.toSet());
        Set<Long> paymentIds = order.getPayments().stream().map(Payment::getPaymentId).collect(Collectors.toSet());
        Set<Long> shippingIds = order.getShippings().stream().map(Shipping::getShippingId).collect(Collectors.toSet());

        return new OrderDTO(order.getOrderId(), order.getCustomer() != null ? order.getCustomer().getCustomerId() : null,
                order.getOrderDate(), order.getTotalAmount(), orderItemIds, paymentIds, shippingIds);
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());

        if (orderDTO.getCustomerId() != null) {
            Customer customer = customerService.findCustomerById(orderDTO.getCustomerId());
            order.setCustomer(customer);
        }

        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());

        Set<OrderItem> orderItems = orderDTO.getOrderItemIds().stream()
                .map(orderItemService::findOrderItemById).collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        Set<Payment> payments = orderDTO.getPaymentIds().stream()
                .map(paymentService::findPaymentById).collect(Collectors.toSet());
        order.setPayments(payments);

        Set<Shipping> shippings = orderDTO.getShippingIds().stream()
                .map(shippingService::findShippingById).collect(Collectors.toSet());
        order.setShippings(shippings);

        return order;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderDTO> orderDTOs = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        if (order != null) {
            OrderDTO orderDTO = convertToDTO(order);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderService.save(order);
        OrderDTO savedOrderDTO = convertToDTO(savedOrder);
        return new ResponseEntity<>(savedOrderDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        Order existingOrder = orderService.findOrderById(id);
        if (existingOrder != null) {
            existingOrder.setOrderDate(orderDTO.getOrderDate());
            existingOrder.setTotalAmount(orderDTO.getTotalAmount());

            if (orderDTO.getCustomerId() != null) {
                Customer customer = customerService.findCustomerById(orderDTO.getCustomerId());
                existingOrder.setCustomer(customer);
            }

            Set<OrderItem> orderItems = orderDTO.getOrderItemIds().stream()
                    .map(orderItemService::findOrderItemById).collect(Collectors.toSet());
            existingOrder.setOrderItems(orderItems);

            Set<Payment> payments = orderDTO.getPaymentIds().stream()
                    .map(paymentService::findPaymentById).collect(Collectors.toSet());
            existingOrder.setPayments(payments);

            Set<Shipping> shippings = orderDTO.getShippingIds().stream()
                    .map(shippingService::findShippingById).collect(Collectors.toSet());
            existingOrder.setShippings(shippings);

            Order updatedOrder = orderService.save(existingOrder);
            OrderDTO updatedOrderDTO = convertToDTO(updatedOrder);
            return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        if (order != null) {
            orderService.deleteOrderById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
