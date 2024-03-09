package org.example.bookingappliation.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T, D> {
    String getKey();

    Specification<T> getSpecification(D params);
}
