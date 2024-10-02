package org.product.restful.api.controller;

import org.info.product.models.Order;
import org.info.product.models.Payment;
import org.product.restful.api.dto.PaymentDTO;
import org.product.info.services.PaymentService;
import org.product.info.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getOrder() != null ? payment.getOrder().getOrderId() : null,
                payment.getPaymentDate(),
                payment.getAmount(),
                payment.getPaymentMethod()
        );
    }

    private Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentDTO.getPaymentId());
        if (paymentDTO.getOrderId() != null) {
            Order order = orderService.findOrderById(paymentDTO.getOrderId());
            payment.setOrder(order);
        }
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        return payment;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayments();
        List<PaymentDTO> paymentDTOs = payments.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(paymentDTOs, HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("id") long id) {
        Payment payment = paymentService.findPaymentById(id);
        if (payment != null) {
            PaymentDTO paymentDTO = convertToDTO(payment);
            return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        Payment savedPayment = paymentService.save(payment);
        PaymentDTO savedPaymentDTO = convertToDTO(savedPayment);
        return new ResponseEntity<>(savedPaymentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable("id") long id, @RequestBody PaymentDTO paymentDTO) {
        Payment existingPayment = paymentService.findPaymentById(id);
        if (existingPayment != null) {
            existingPayment.setAmount(paymentDTO.getAmount());
            existingPayment.setPaymentDate(paymentDTO.getPaymentDate());
            existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());

            if (paymentDTO.getOrderId() != null) {
                Order order = orderService.findOrderById(paymentDTO.getOrderId());
                existingPayment.setOrder(order);
            }

            Payment updatedPayment = paymentService.save(existingPayment);
            PaymentDTO updatedPaymentDTO = convertToDTO(updatedPayment);
            return new ResponseEntity<>(updatedPaymentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") long id) {
        Payment payment = paymentService.findPaymentById(id);
        if (payment != null) {
            paymentService.deletePaymentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
