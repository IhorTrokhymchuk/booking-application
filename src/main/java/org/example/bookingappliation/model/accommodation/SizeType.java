package org.example.bookingappliation.model.accommodation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;

@Data
@NoArgsConstructor
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

    public SizeType(Long id) {
        if(id > SizeTypeName.values().length) {
            throw new EntityNotFoundException("Invalid size type id: " + id);
        }
        this.id = id;
    }

    //THIS EXAMPLE TO USE
    public enum SizeTypeName {
        ONE_PEOPLE,
        TWO_PEOPLE,
        THREE_PEOPLE,
        FOUR_PEOPLE
    }
}
