package org.example.bookingappliation.service;

import java.util.List;

import org.example.bookingappliation.dto.bookings.request.BookingPatchRequestDto;
import org.example.bookingappliation.dto.bookings.request.BookingRequestDto;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookingService {
    BookingDto save(BookingRequestDto requestDto, String email);

    List<BookingDto> getByUserEmail(String email, Pageable pageable);

    BookingDto getById(Long id, String email);

    BookingDto updateInfo(Long id, BookingRequestDto requestDto, String email);

    BookingDto cancel(Long id, String email);
}
