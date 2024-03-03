package org.example.bookingappliation.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;
import org.example.bookingappliation.dto.AddressRequestDto;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.mapper.AccommodationMapper;
import org.example.bookingappliation.mapper.AddressMapper;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.Address;
import org.example.bookingappliation.repository.AccommodationRepository;
import org.example.bookingappliation.repository.AddressRepository;
import org.example.bookingappliation.service.AccommodationService;
import org.example.bookingappliation.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public AccommodationDto save(AccommodationRequestDto requestDto) {
        Accommodation accommodation = getAccommodationWithAddress(requestDto);
        accommodationRepository.save(accommodation);
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    public AccommodationDto getById(Long id) {
        return accommodationMapper.toDto(getAccommodationById(id));
    }

    @Override
    public List<AccommodationDto> getAll(Pageable pageable) {
        Page<Accommodation> accommodationList = accommodationRepository.findAll(pageable);
        if (accommodationList.isEmpty()) {
            throw new EntityNotFoundException("Can't find any accommodation");
        }
        return accommodationList.stream()
                .map(accommodationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AccommodationDto update(Long id, AccommodationRequestDto requestDto) {
        Accommodation accommodation = getAccommodationById(id);
        updateAccommodationWithoutAddress(accommodation, requestDto);
        updateAccommodationAddress(accommodation, requestDto);
        accommodationRepository.save(accommodation);
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    public void deleteById(Long id) {
        getAccommodationById(id);
        accommodationRepository.deleteById(id);
    }

    private void updateAccommodationWithoutAddress(Accommodation accommodation,
                                                   AccommodationRequestDto requestDto) {
        Accommodation updateAccommodation = accommodationMapper.toModelWithoutAddress(requestDto);
        BeanUtils.copyProperties(updateAccommodation, accommodation, "id", "address", "isDeleted");
    }

    private void updateAccommodationAddress(Accommodation accommodation,
                                            AccommodationRequestDto requestDto) {
        Address address = accommodation.getAddress();
        Address updateAddress = addressMapper.toModel(requestDto.getAddressDto());
        BeanUtils.copyProperties(updateAddress, address, "id", "isDeleted");
        accommodation.setAddress(addressRepository.save(address));
    }

    private Accommodation getAccommodationById(Long id) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(id);
        if (accommodationOptional.isEmpty()) {
            throw new EntityNotFoundException("Cant find accommodation with id: " + id);
        }
        return accommodationOptional.get();
    }

    private Accommodation getAccommodationWithAddress(AccommodationRequestDto requestDto) {
        Accommodation accommodation = accommodationMapper.toModelWithoutAddress(requestDto);
        AddressRequestDto addressRequestDto = requestDto.getAddressDto();
        Address address = addressService.save(addressRequestDto);
        accommodation.setAddress(address);
        return accommodation;
    }
}
