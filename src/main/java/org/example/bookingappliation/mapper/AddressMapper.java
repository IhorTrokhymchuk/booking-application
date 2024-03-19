package org.example.bookingappliation.mapper;

import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.addresses.request.AddressRequestDto;
import org.example.bookingappliation.dto.addresses.response.AddressDto;
import org.example.bookingappliation.model.accommodation.Address;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {
    AddressDto toDto(Address address);

    Address toModel(AddressRequestDto requestDto);
}
