package org.product.info.services;

import org.info.product.models.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAllPayments();

    Payment findPaymentById(long id);

    Payment save(Payment payment);

    void delete(Payment payment);

    void deletePaymentById(long id);
}
