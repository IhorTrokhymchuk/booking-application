package org.example.bookingappliation.model.accommodation;

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

@Data
@NoArgsConstructor
@Entity
@Table(name = "accommodation_types")
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private AccommodationTypeName name;

    public AccommodationType(Long id) {
        if (id > AccommodationTypeName.values().length) {
            throw new EntityNotFoundException("Invalid accommodation type id: " + id);
        }
        this.id = id;
    }

    public enum AccommodationTypeName {
        HOUSE,
        APARTMENT,
        CONDO,
        VACATION_HOME,
        HOTEL,
        HOSTEL
    }
}
