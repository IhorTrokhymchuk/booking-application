package org.example.bookingappliation.repository.accommodation.specefications;


import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.example.bookingappliation.model.accommodation.AmenityType;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AmenityTypeSpecificationProvider implements SpecificationProvider<Accommodation, Long[]> {
    private static final String TYPE_IDS_KEY = "amenityTypes";
    private static final String SIZE_TYPE_FIELD_NAME = "amenities";

    @Override
    public String getKey() {
        return TYPE_IDS_KEY;
    }

    @Override
    public Specification<Accommodation> getSpecification(Long[] params) {

        return (root, query, criteriaBuilder) -> root.join(SIZE_TYPE_FIELD_NAME)
            .in(Arrays.stream(params).map(AmenityType::new).toArray());
    }
}
