package org.example.bookingappliation.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.model.accommodation.SizeType;
import org.example.bookingappliation.repository.SizeTypeRepository;
import org.example.bookingappliation.service.SizeTypeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeTypeServiceImpl implements SizeTypeService {
    private final SizeTypeRepository sizeTypeRepository;

    @Override
    public SizeType getById(Long id) {
        Optional<SizeType> sizeType = sizeTypeRepository.findById(id);
        if (sizeType.isEmpty()) {
            throw new EntityNotFoundException("Cant find size type with id: " + id);
        }
        return sizeType.get();
    }
}
