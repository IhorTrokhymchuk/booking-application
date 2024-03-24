package org.example.bookingappliation.dto.payment.responce;

import java.math.BigDecimal;
import lombok.Data;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;

@Data
public class PaymentDto {
    private String status;
    private BookingDto booking;
    private BigDecimal amount;
}
