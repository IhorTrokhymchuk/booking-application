package org.example.bookingappliation.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;
import org.example.bookingappliation.dto.AddressRequestDto;
import org.example.bookingappliation.mapper.AccommodationMapper;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.Address;
import org.example.bookingappliation.repository.AccommodationRepository;
import org.example.bookingappliation.service.AccommodationService;
import org.example.bookingappliation.service.AddressService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final AddressService addressService;

    @Override
    @Transactional
    public AccommodationDto save(AccommodationRequestDto requestDto) {
        Accommodation accommodation = getAccommodationWithAddress(requestDto);
        accommodationRepository.save(accommodation);
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    public AccommodationDto getById(Long id) {
        return null;
    }

    @Override
    public AccommodationDto findAll() {
        return null;
    }

    @Override
    public AccommodationDto update(AccommodationRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private Accommodation getAccommodationWithAddress(AccommodationRequestDto requestDto) {
        Accommodation accommodation = accommodationMapper.toModelWithoutAddress(requestDto);
        AddressRequestDto addressRequestDto = requestDto.getAddressDto();
        Address address = addressService.save(addressRequestDto);
        accommodation.setAddress(address);
        return accommodation;
    }
}
