package org.example.bookingappliation.dto.bookings.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.bookingappliation.dto.checkdate.request.CheckDateRequestDto;

@Data
public class BookingPatchRequestDto {
    @NotNull
    private CheckDateRequestDto checkDates;
    @NotNull
    private Long accommodationId;
}
