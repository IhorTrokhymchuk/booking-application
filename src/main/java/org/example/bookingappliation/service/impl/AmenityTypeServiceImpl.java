package org.example.bookingappliation.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.model.accommodation.AmenityType;
import org.example.bookingappliation.repository.AmenityTypeRepository;
import org.example.bookingappliation.service.AmenityTypeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmenityTypeServiceImpl implements AmenityTypeService {
    private final AmenityTypeRepository amenityTypeRepository;

    @Override
    public AmenityType getById(Long id) {
        Optional<AmenityType> accommodationType = amenityTypeRepository.findById(id);
        if (accommodationType.isEmpty()) {
            throw new EntityNotFoundException("Cant find amenity type with id: " + id);
        }
        return accommodationType.get();
    }
}
