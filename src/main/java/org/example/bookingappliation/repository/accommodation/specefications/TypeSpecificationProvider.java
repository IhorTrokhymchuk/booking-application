package org.example.bookingappliation.repository.accommodation.specefications;


import org.example.bookingappliation.model.accommodation.Accommodation;

import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TypeSpecificationProvider implements SpecificationProvider<Accommodation, Long[]> {
    private static final String TYPE_IDS_KEY = "types";
    private static final String TYPE_FIELD_NAME = "type";

    @Override
    public String getKey() {
        return TYPE_IDS_KEY;
    }

    @Override
    public Specification<Accommodation> getSpecification(Long[] params) {
        return (root, query, criteriaBuilder) -> root.get(TYPE_FIELD_NAME)
            .in(Arrays.stream(params).map(AccommodationType::new).toArray());
    }
}
