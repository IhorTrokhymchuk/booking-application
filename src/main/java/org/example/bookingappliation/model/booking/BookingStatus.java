package org.example.bookingappliation.model.booking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;

@Entity
@Data
@Table(name = "booking_status")
@NoArgsConstructor
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private BookingStatusName name;

    public BookingStatus(Long id) {
        if (id > BookingStatus.BookingStatusName.values().length || id < 0) {
            throw new EntityNotFoundException("Invalid amenity type id: " + id);
        }
        this.id = id;
    }

    public enum BookingStatusName {
        PENDING,
        CONFIRMED,
        CANCELED,
        EXPIRED
    }
}
