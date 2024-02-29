package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
