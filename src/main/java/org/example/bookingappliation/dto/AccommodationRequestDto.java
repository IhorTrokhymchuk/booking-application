package org.example.bookingappliation.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class AccommodationRequestDto {
    @NotNull
    private Long typeId;
    @NotNull
    private AddressRequestDto addressDto;
    @NotNull
    private Long sizeId;
    @NotNull
    private Set<Long> amenityTypeIds;
    @NotNull
    private BigDecimal dailyRate;
}
