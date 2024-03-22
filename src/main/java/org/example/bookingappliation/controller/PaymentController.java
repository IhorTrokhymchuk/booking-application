package org.example.bookingappliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment management", description = "Endpoints manage payment")
@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get payment URL",
            description = "Get payment URL")
    public String createPaymentIntent(@PathVariable Long id, Authentication authentication) {
        return paymentService.createPaymentCheckoutSession(id, authentication.getName());
    }
}
