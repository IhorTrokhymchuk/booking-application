package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
