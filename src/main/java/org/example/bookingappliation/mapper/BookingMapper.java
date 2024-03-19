package org.example.bookingappliation.mapper;

import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.bookings.request.BookingRequestDto;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;
import org.example.bookingappliation.model.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class,
        uses = {UserMapper.class, AccommodationMapper.class, CheckDateMapper.class})
public interface BookingMapper {
    @Mapping(target = "bookingStatus", source = "status.name")
    BookingDto toDto(Booking booking);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    Booking toModelWithoutStatusAndUser(BookingRequestDto requestDto);

    void setUpdateInfoToBooking(@MappingTarget Booking booking, BookingRequestDto requestDto);
}
