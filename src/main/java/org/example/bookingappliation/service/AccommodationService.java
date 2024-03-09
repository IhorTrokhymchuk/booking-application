package org.example.bookingappliation.service;

import java.util.List;

import org.example.bookingappliation.dto.accommodations.request.AccommodationSearchDto;
import org.example.bookingappliation.dto.accommodations.response.AccommodationDto;
import org.example.bookingappliation.dto.accommodations.request.AccommodationRequestDto;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    AccommodationDto save(AccommodationRequestDto requestDto);

    AccommodationDto getById(Long id);

    List<AccommodationDto> getAll(Pageable pageable);

    List<AccommodationDto> search(Pageable pageable, AccommodationSearchDto requestDto);

    AccommodationDto update(Long id, AccommodationRequestDto requestDto);

    void deleteById(Long id);
}
