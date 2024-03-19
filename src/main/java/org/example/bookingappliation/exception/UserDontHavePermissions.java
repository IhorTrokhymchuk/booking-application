package org.example.bookingappliation.exception;

public class UserDontHavePermissions extends RuntimeException {
    public UserDontHavePermissions(String message) {
        super(message);
    }
}
