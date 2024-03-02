package org.example.bookingappliation.model.accommodation;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@SQLDelete(sql = "UPDATE accommodations SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private AccommodationType type;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", unique = true, nullable = false)
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_type_id", nullable = false)
    private SizeType size;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accommodations_amenity_types",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_type_id")
    )
    private Set<AmenityType> amenities;
    @Column(name = "daily_rate", nullable = false)
    private BigDecimal dailyRate;
    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted = false;
}
