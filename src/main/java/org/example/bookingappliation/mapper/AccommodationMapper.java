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
import org.example.bookingappliation.service.AccommodationTypeService;
import org.example.bookingappliation.service.AmenityTypeService;
import org.example.bookingappliation.service.SizeTypeService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = MapperConfig.class, uses = {
        AddressMapper.class,
        AccommodationTypeService.class,
        SizeTypeService.class,
        AmenityTypeService.class
})
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

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Accommodation toModelWithoutAddress(AccommodationRequestDto requestDto);

    @AfterMapping
    default void setAccommodationType(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto,
            @Context AccommodationTypeService accommodationTypeService
    ) {
        AccommodationType accommodationType
                = accommodationTypeService.getById(requestDto.getTypeId());
        accommodation.setType(accommodationType);
    }

    @AfterMapping
    default void setSizeType(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto,
            @Context SizeTypeService sizeTypeService
    ) {
        SizeType sizeType = sizeTypeService.getById(requestDto.getSizeId());
        accommodation.setSize(sizeType);
    }

    @AfterMapping
    default void setAmenityTypes(
            @MappingTarget Accommodation accommodation,
            AccommodationRequestDto requestDto,
            @Context AmenityTypeService amenityTypeService) {
        Set<AmenityType> setOfAmenityType = requestDto.getAmenityTypeIds().stream()
                .map(amenityTypeService::getById)
                .collect(Collectors.toSet());
        accommodation.setAmenities(setOfAmenityType);
    }
}
