package org.product.restful.api.controller;

import org.info.product.models.Payment;
import org.product.info.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") long id) {
        Payment payment = paymentService.findPaymentById(id);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.save(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    // PUT: Update an existing payment
    @PutMapping("/update/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") long id, @RequestBody Payment paymentDetails) {
        Payment existingPayment = paymentService.findPaymentById(id);
        if (existingPayment != null) {
            existingPayment.setAmount(paymentDetails.getAmount());
            existingPayment.setOrder(paymentDetails.getOrder());
            existingPayment.setPaymentDate(paymentDetails.getPaymentDate());
            existingPayment.setPaymentMethod(paymentDetails.getPaymentMethod());

            Payment updatedPayment = paymentService.save(existingPayment);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
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

