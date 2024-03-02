package org.example.bookingappliation.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccommodationRequestDto {
    @NotNull
    @Positive
    private Long typeId;
    @NotNull
    private AddressRequestDto addressDto;
    @NotNull
    @Positive
    private Long sizeId;
    @NotNull
    private Set<Long> amenityTypeIds;
    @NotNull
    @Positive
    private BigDecimal dailyRate;
}