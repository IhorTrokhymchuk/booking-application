package org.example.bookingappliation.repository.payment;

import org.example.bookingappliation.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}