package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.addresses.request.AddressRequestDto;
import org.example.bookingappliation.model.accommodation.Address;

public interface AddressService {
    Address save(AddressRequestDto requestDto);
}
