package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.payment.responce.PaymentDto;

public interface PaymentService {
    String createPaymentCheckoutSession(Long id, String email);

    PaymentDto successPayment(Long bookingId, String email);

    PaymentDto cancelPaymentAndBooking(Long bookingId, String email);
}
