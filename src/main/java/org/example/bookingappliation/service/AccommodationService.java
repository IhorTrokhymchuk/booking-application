package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationService {
    AccommodationDto save(AccommodationRequestDto requestDto);

    AccommodationDto getById(Long id);

    List<AccommodationDto> getAll(Pageable pageable);

    AccommodationDto update(Long id, AccommodationRequestDto requestDto);

    void deleteById(Long id);
}
