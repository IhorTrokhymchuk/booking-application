package org.example.bookingappliation.model.accommodation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;

@Data
@NoArgsConstructor
@Entity
@Table(name = "amenity_types")
public class AmenityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private AmenityTypeName name;

    public AmenityType(Long id) {
        if(id > AmenityType.AmenityTypeName.values().length) {
            throw new EntityNotFoundException("Invalid amenity type id: " + id);
        }
        this.id = id;
    }

    public enum AmenityTypeName {
        SWIMMING_POOL,
        GYM,
        FREE_WIFI,
        PARKING,
        BREAKFAST_INCLUDED,
        PET_FRIENDLY
    }
}
