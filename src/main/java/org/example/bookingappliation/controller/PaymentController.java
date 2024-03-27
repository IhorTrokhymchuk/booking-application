package org.example.bookingappliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.payment.responce.PaymentDto;
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

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get payment URL",
            description = "Get payment URL by booking id")
    public String createPaymentIntent(@PathVariable Long id, Authentication authentication) {
        return paymentService.createPaymentCheckoutSession(id, authentication.getName());
    }

    @GetMapping("/success/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Success payment ",
            description = "Success payment by session id")
    public PaymentDto successPayments(@PathVariable Long id, Authentication authentication) {
        return paymentService.successPayment(id, authentication.getName());
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Cancel payment ",
            description = "Cancel payment by booking id")
    public PaymentDto cancelPayments(@PathVariable Long id, Authentication authentication) {
        return paymentService.cancelPaymentAndBooking(id, authentication.getName());
    }
}
