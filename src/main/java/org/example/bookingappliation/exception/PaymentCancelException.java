package org.example.bookingappliation.exception;

public class PaymentCancelException extends RuntimeException {
    public PaymentCancelException(String message) {
        super(message);
    }
}
