package org.example.bookingappliation.mapper;

import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.payment.responce.PaymentDto;
import org.example.bookingappliation.model.payment.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookingMapper.class)
public interface PaymentMapper {
    @Mapping(target = "status", source = "status.name")
    PaymentDto toDto(Payment payment);
}
