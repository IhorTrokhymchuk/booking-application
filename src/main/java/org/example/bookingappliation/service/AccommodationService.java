package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;

public interface AccommodationService {
    AccommodationDto save(AccommodationRequestDto requestDto);

    AccommodationDto getById(Long id);

    AccommodationDto findAll();

    AccommodationDto update(AccommodationRequestDto requestDto);

    void deleteById(Long id);
}
