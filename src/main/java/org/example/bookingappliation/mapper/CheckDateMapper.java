package org.example.bookingappliation.mapper;

import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.checkdate.request.CheckDateRequestDto;
import org.example.bookingappliation.model.booking.CheckDate;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CheckDateMapper {
    CheckDate toModel(CheckDateRequestDto requestDto);

    CheckDateRequestDto toDto(CheckDate checkDate);
}
