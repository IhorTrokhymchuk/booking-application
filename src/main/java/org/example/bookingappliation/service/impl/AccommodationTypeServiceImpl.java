package org.example.bookingappliation.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.example.bookingappliation.repository.accommodationtype.AccommodationTypeRepository;
import org.example.bookingappliation.service.AccommodationTypeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationTypeServiceImpl implements AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;

    @Override
    public AccommodationType getById(Long id) {
        Optional<AccommodationType> accommodationType = accommodationTypeRepository.findById(id);
        if (accommodationType.isEmpty()) {
            throw new EntityNotFoundException("Cant find accommodation type with id: " + id);
        }
        return accommodationType.get();
    }
}
