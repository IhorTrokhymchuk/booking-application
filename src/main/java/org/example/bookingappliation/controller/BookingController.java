package org.example.bookingappliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.bookings.request.BookingRequestDto;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;
import org.example.bookingappliation.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Booking management", description = "Endpoints to managing bookings")
@RestController
@RequestMapping(value = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Booking",
            description = "create a bookings")
    public BookingDto save(@RequestBody BookingRequestDto requestDto,
                           Authentication authentication) {
        return bookingService.save(requestDto, authentication.getName());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get all bookings",
            description = "Get a page of all available bookings")
    public List<BookingDto> getUserBookings(Authentication authentication, Pageable pageable) {
        return bookingService.getByUserEmail(authentication.getName(), pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get booking by id",
            description = "Get a booking by id if this you booking")
    public BookingDto getUserBookings(@PathVariable Long id, Authentication authentication) {
        return bookingService.getById(id, authentication.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Update bookings",
            description = "Update booking status, checkIn and checkOut date")
    public BookingDto updateDate(@PathVariable Long id,
                                 @RequestBody BookingRequestDto requestDto,
                                 Authentication authentication) {
        return bookingService.updateInfo(id, requestDto, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Cancel bookings",
            description = "Cancel booking if is have status pending")
    public BookingDto updateDate(@PathVariable Long id,
                                 Authentication authentication) {
        return bookingService.cancel(id, authentication.getName());
    }
}
