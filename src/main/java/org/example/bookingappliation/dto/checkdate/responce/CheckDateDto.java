package org.example.bookingappliation.dto.checkdate.responce;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CheckDateDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
