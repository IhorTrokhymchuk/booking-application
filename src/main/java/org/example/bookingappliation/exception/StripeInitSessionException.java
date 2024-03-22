package org.example.bookingappliation.exception;

public class StripeInitSessionException extends RuntimeException {
    public StripeInitSessionException(String message) {
        super(message);
    }
}
