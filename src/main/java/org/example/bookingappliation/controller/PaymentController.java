package org.example.bookingappliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.payment.responce.PaymentDto;
import org.example.bookingappliation.service.BookingService;
import org.example.bookingappliation.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment management", description = "Endpoints manage payment")
@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get payment URL",
            description = "Get payment URL")
    public String createPaymentIntent(@PathVariable Long bookingId, Authentication authentication) {
        return paymentService.createPaymentCheckoutSession(bookingId, authentication.getName());
    }

    @GetMapping("/success/{bookingId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "success payment ",
            description = "success payment by session id")
    public PaymentDto successPayments(@PathVariable Long bookingId, Authentication authentication) {
        return paymentService.successPayment(bookingId, authentication.getName());
    }

    @GetMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "success payment ",
            description = "success payment by session id")
    public PaymentDto cancelPayments(@PathVariable Long bookingId, Authentication authentication) {
        return paymentService.cancelPaymentAndBooking(bookingId, authentication.getName());
    }

}
