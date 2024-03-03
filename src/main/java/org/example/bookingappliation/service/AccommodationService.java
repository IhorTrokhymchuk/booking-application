package org.example.bookingappliation.service;

import java.util.List;
import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    AccommodationDto save(AccommodationRequestDto requestDto);

    AccommodationDto getById(Long id);

    List<AccommodationDto> getAll(Pageable pageable);

    AccommodationDto update(Long id, AccommodationRequestDto requestDto);

    void deleteById(Long id);
}
