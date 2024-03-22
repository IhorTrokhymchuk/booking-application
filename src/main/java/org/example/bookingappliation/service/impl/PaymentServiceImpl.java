package org.example.bookingappliation.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.exception.StripeInitSessionException;
import org.example.bookingappliation.exception.UserDontHavePermissions;
import org.example.bookingappliation.model.booking.Booking;
import org.example.bookingappliation.model.booking.CheckDate;
import org.example.bookingappliation.model.payment.Payment;
import org.example.bookingappliation.model.payment.PaymentStatus;
import org.example.bookingappliation.repository.booking.BookingRepository;
import org.example.bookingappliation.repository.payment.PaymentRepository;
import org.example.bookingappliation.repository.paymentstatus.PaymentStatusRepository;
import org.example.bookingappliation.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final SessionCreateParams.PaymentMethodType PAYMENT_METHOD
            = SessionCreateParams.PaymentMethodType.CARD;
    private static final PaymentStatus.PaymentStatusName PENDING_STATUS
            = PaymentStatus.PaymentStatusName.PENDING;
    private static final SessionCreateParams.Mode SESSION_MODE
            = SessionCreateParams.Mode.PAYMENT;
    private static final String CURRENCY = "USD";
    private static final String PRODUCT_NAME = "Housing reservation";
    private static final String SUCCESS_URL = "https://docs.stripe.com/workbench";
    private static final String CANCEL_URL = "https://stripe.partners";
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public String createPaymentCheckoutSession(Long id, String email) {
        Booking booking = getBookingById(id);
        checkBookingUser(email, booking);
        SessionCreateParams sessionParams = getSessionParams(booking);
        Session stripeSession = getStripeSession(sessionParams);
        Payment payment = createPayment(stripeSession, booking);
        paymentRepository.save(payment);
        return stripeSession.getUrl();
    }

    private SessionCreateParams getSessionParams(Booking booking) {
        return new SessionCreateParams.Builder()
                .addPaymentMethodType(PAYMENT_METHOD)
                .addLineItem(getLineItem(booking))
                .setMode(SESSION_MODE)
                .setSuccessUrl(SUCCESS_URL)
                .setCancelUrl(CANCEL_URL)
                .build();
    }

    private SessionCreateParams.LineItem getLineItem(Booking booking) {
        //BUILD PARAMS
        return new SessionCreateParams.LineItem.Builder()
                .setPriceData(new SessionCreateParams.LineItem.PriceData.Builder()
                        .setCurrency(CURRENCY)
                        .setUnitAmount(getAmount(booking))
                        .setProductData(new SessionCreateParams
                                .LineItem.PriceData.ProductData.Builder()
                                .setName(PRODUCT_NAME)
                                .setDescription(booking.toString())
                                .build())
                        .build())
                .setQuantity(getQuantity(booking.getCheckDates()))
                .build();
    }

    private Session getStripeSession(SessionCreateParams sessionParams) {
        Session session;
        try {
            session = Session.create(sessionParams);
        } catch (StripeException e) {
            throw new StripeInitSessionException("Cant initialize Stripe payment session");
        }
        return session;
    }

    private Payment createPayment(Session session, Booking booking) {
        Payment newPayment = new Payment();
        newPayment.setSessionUrl(session.getUrl());
        newPayment.setSessionId(session.getId());
        newPayment.setBooking(booking);
        newPayment.setAmount(BigDecimal.valueOf(session.getAmountTotal()));
        PaymentStatus pendingStatus
                = paymentStatusRepository.findPaymentStatusByName(PENDING_STATUS);
        newPayment.setStatus(pendingStatus);
        return newPayment;
    }

    private Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find entity with id: " + id));
    }

    private void checkBookingUser(String email, Booking booking) {
        if (!email.equals(booking.getUser().getEmail())) {
            throw new UserDontHavePermissions("User with email: " + email
                    + " cant have permission to booking with id: " + booking.getId());
        }
    }

    private long getAmount(Booking booking) {
        BigDecimal dailyRate = booking.getAccommodation().getDailyRate();
        return dailyRate.multiply(BigDecimal.valueOf(100L)).longValue();
    }

    private long getQuantity(CheckDate checkDate) {
        LocalDate checkInDate = checkDate.getCheckInDate();
        LocalDate checkOutDate = checkDate.getCheckOutDate();
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
