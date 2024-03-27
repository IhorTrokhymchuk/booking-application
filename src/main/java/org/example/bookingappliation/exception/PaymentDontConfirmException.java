package org.example.bookingappliation.exception;

public class PaymentDontConfirmException extends RuntimeException {
    public PaymentDontConfirmException(String message) {
        super(message);
    }
}
