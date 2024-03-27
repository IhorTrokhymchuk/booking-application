package org.example.bookingappliation.exception;

public class CantPaidBookingException extends RuntimeException {
    public CantPaidBookingException(String message) {
        super(message);
    }
}
