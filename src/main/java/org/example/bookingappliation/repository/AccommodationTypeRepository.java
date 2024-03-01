package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
}
