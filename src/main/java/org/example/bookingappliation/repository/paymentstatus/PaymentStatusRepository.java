package org.example.bookingappliation.repository.paymentstatus;

import org.example.bookingappliation.model.payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    PaymentStatus findPaymentStatusByName(PaymentStatus.PaymentStatusName name);
}
