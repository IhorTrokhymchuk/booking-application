package org.example.bookingappliation.repository.booking;

import java.time.LocalDate;
import java.util.List;
import org.example.bookingappliation.model.booking.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) FROM Booking b "
            + "WHERE b.accommodation.id = :accommodationId "
            + "AND NOT b.status.name = 'CANCELED' "
            + "AND NOT b.status.name = 'EXPIRED' "
            + "AND (:checkInDate BETWEEN b.checkDates.checkInDate AND b.checkDates.checkOutDate "
            + "OR :checkOutDate BETWEEN b.checkDates.checkInDate AND b.checkDates.checkOutDate)")
    Long isDatesAvailableForAccommodation(@Param("accommodationId") Long accommodationId,
                                             @Param("checkInDate") LocalDate checkInDate,
                                             @Param("checkOutDate") LocalDate checkOutDate);

    List<Booking> findAllByUser_Email(String email, Pageable pageable);
}
