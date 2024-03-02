package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.accommodation.Accommodation;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a "
            + "LEFT JOIN FETCH a.type "
            + "LEFT JOIN FETCH a.address "
            + "LEFT JOIN FETCH a.amenities "
            + "LEFT JOIN FETCH a.size "
            + "WHERE a.id = :id")
    Optional<Accommodation> findById(Long id);

    @Query("SELECT a FROM Accommodation a "
            + "LEFT JOIN FETCH a.type "
            + "LEFT JOIN FETCH a.address "
            + "LEFT JOIN FETCH a.amenities "
            + "LEFT JOIN FETCH a.size")
    Page<Accommodation> findAll(Pageable pageable);
}
