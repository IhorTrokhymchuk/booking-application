package org.example.bookingappliation.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.AccommodationDto;
import org.example.bookingappliation.dto.AccommodationRequestDto;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.AccommodationType;
import org.example.bookingappliation.model.accommodation.AmenityType;
import org.example.bookingappliation.model.accommodation.SizeType;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = AddressMapper.class)
public interface AccommodationMapper {
    @Mapping(target = "typeId", source = "type.id")
    @Mapping(target = "sizeId", source = "size.id")
    @Mapping(target = "amenityTypeIds", ignore = true)
    AccommodationDto toDto(Accommodation accommodation);

    @AfterMapping
    default void setAmenityTypeIds(
            @MappingTarget AccommodationDto accommodationDto,
            Accommodation accommodation) {
        Set<Long> setOfAmenityTypeIds = accommodation.getAmenities().stream()
                .map(AmenityType::getId)
                .collect(Collectors.toSet());
        accommodationDto.setAmenityTypeIds(setOfAmenityTypeIds);
    }

    Accommodation toModelWithoutAddress(AccommodationRequestDto requestDto);

    @AfterMapping
    default void setAccommodationType(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto) {
        AccommodationType accommodationType = new AccommodationType(requestDto.getTypeId());
        accommodation.setType(accommodationType);
    }

    @AfterMapping
    default void setSizeType(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto) {
        SizeType sizeType =  new SizeType(requestDto.getSizeId());
        accommodation.setSize(sizeType);
    }

    @AfterMapping
    default void setAmenityTypes(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto) {
        Set<AmenityType> setOfAmenityType = requestDto.getAmenityTypeIds().stream()
                .map(AmenityType::new)
                .collect(Collectors.toSet());
        accommodation.setAmenities(setOfAmenityType);
    }
}
