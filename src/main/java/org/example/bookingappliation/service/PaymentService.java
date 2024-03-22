package org.example.bookingappliation.service;

public interface PaymentService {
    String createPaymentCheckoutSession(Long id, String email);
}
