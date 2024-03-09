package org.example.bookingappliation.dto.accommodations.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccommodationSearchDto {
    private Long[] typeIds;
    private Long[] sizeTypeIds;
    private Long[] amenityTypeIds;
    private BigDecimal dailyRateMin;
    private BigDecimal dailyRateMax;
}
