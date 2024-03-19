package org.example.bookingappliation.dto.bookings.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.bookingappliation.dto.checkdate.request.CheckDateRequestDto;

@Data
public class BookingRequestDto {
    @NotNull
    private CheckDateRequestDto checkDates;
    @Positive
    private Long accommodationId;
}
