package org.example.bookingappliation.dto.bookings.responce;

import lombok.Data;
import org.example.bookingappliation.dto.accommodations.response.AccommodationDto;
import org.example.bookingappliation.dto.checkdate.responce.CheckDateDto;
import org.example.bookingappliation.dto.users.response.UserResponseDto;

@Data
public class BookingStripeDto {
    private CheckDateDto checkDates;
    private AccommodationDto accommodation;
    private String email;
    private String bookingStatus;
}
