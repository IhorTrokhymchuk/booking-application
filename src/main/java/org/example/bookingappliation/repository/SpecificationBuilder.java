package org.example.bookingappliation.repository;

import org.example.bookingappliation.dto.accommodations.request.AccommodationSearchDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(AccommodationSearchDto requestDto);
}
