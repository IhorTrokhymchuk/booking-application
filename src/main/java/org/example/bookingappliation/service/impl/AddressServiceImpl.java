package org.example.bookingappliation.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.addresses.request.AddressRequestDto;
import org.example.bookingappliation.mapper.AddressMapper;
import org.example.bookingappliation.model.accommodation.Address;
import org.example.bookingappliation.repository.address.AddressRepository;
import org.example.bookingappliation.service.AddressService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public Address save(AddressRequestDto requestDto) {
        Address address = addressMapper.toModel(requestDto);
        return addressRepository.save(address);
    }
}
