package org.example.bookingappliation.dto.accommodations.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccommodationSearchDto {
    private Long[] typeIds;
    private Long[] sizeTypeIds;
    private Long[] amenityTypeIds;
    private BigDecimal dailyRateMin;
    private BigDecimal dailyRateMax;
}
