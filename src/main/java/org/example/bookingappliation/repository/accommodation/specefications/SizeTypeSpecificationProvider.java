package org.example.bookingappliation.repository.accommodation.specefications;


import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.example.bookingappliation.model.accommodation.SizeType;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SizeTypeSpecificationProvider implements SpecificationProvider<Accommodation, Long[]> {
    private static final String SIZE_TYPE_IDS_KEY = "sizeTypes";
    private static final String SIZE_TYPE_FIELD_NAME = "size";

    @Override
    public String getKey() {
        return SIZE_TYPE_IDS_KEY;
    }

    @Override
    public Specification<Accommodation> getSpecification(Long[] params) {

        return (root, query, criteriaBuilder) -> root.get(SIZE_TYPE_FIELD_NAME)
            .in(Arrays.stream(params).map(SizeType::new).toArray());
    }
}
