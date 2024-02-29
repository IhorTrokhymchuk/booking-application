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

@Data
@Entity
@Table(name = "size_types")
public class SizeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private SizeTypeName name;

    //THIS EXAMPLE TO USE
    public enum SizeTypeName {
        ONE_PEOPLE,
        TWO_PEOPLE,
        THREE_PEOPLE,
        FOUR_PEOPLE
    }
}
