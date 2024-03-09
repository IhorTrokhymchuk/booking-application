package org.example.bookingappliation.repository.booking;

import org.example.bookingappliation.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
