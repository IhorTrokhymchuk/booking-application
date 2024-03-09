package org.example.bookingappliation.dto.accommodations.response;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.example.bookingappliation.dto.addresses.response.AddressDto;

@Data
public class AccommodationDto {
    private Long id;
    private Long typeId;
    private AddressDto address;
    private Long sizeId;
    private Set<Long> amenityTypeIds;
    private BigDecimal dailyRate;
}
