package org.example.bookingappliation.dto.payment.responce;

import lombok.Data;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;

@Data
public class PaymentStripeDescription {
    private BookingDto booking;

}
