package org.example.bookingappliation.repository.bookingstatus;

import org.example.bookingappliation.model.booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {
    BookingStatus findBookingStatusByName(BookingStatus.BookingStatusName name);
}
