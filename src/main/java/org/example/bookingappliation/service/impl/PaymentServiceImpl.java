package org.example.bookingappliation.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.payment.responce.PaymentDto;
import org.example.bookingappliation.exception.CantPaidBookingException;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.exception.PaymentCancelException;
import org.example.bookingappliation.exception.PaymentDontConfirmException;
import org.example.bookingappliation.exception.StripeSessionException;
import org.example.bookingappliation.exception.UserDontHavePermissions;
import org.example.bookingappliation.mapper.PaymentMapper;
import org.example.bookingappliation.model.booking.Booking;
import org.example.bookingappliation.model.booking.BookingStatus;
import org.example.bookingappliation.model.booking.CheckDate;
import org.example.bookingappliation.model.payment.Payment;
import org.example.bookingappliation.model.payment.PaymentStatus;
import org.example.bookingappliation.repository.booking.BookingRepository;
import org.example.bookingappliation.repository.bookingstatus.BookingStatusRepository;
import org.example.bookingappliation.repository.payment.PaymentRepository;
import org.example.bookingappliation.repository.paymentstatus.PaymentStatusRepository;
import org.example.bookingappliation.service.BookingService;
import org.example.bookingappliation.service.MassageConfigurator;
import org.example.bookingappliation.service.PaymentService;
import org.springframework.stereotype.Service;
//TODO DTO FOR SUCCESSES PAYMENT

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final SessionCreateParams.PaymentMethodType PAYMENT_METHOD
            = SessionCreateParams.PaymentMethodType.CARD;
    private static final SessionCreateParams.Mode SESSION_MODE
            = SessionCreateParams.Mode.PAYMENT;
    private static final PaymentStatus.PaymentStatusName PENDING_PAYMENT_STATUS
            = PaymentStatus.PaymentStatusName.PENDING;
    private static final PaymentStatus.PaymentStatusName PAID_PAYMENT_STATUS
            = PaymentStatus.PaymentStatusName.PAID;
    private static final PaymentStatus.PaymentStatusName CANCEL_PAYMENT_STATUS =
            PaymentStatus.PaymentStatusName.CANCEL;
    private static final BookingStatus.BookingStatusName PENDING_BOOKING_STATUS
            = BookingStatus.BookingStatusName.PENDING;
    private static final BookingStatus.BookingStatusName CONFIRMED_BOOKING_STATUS
            = BookingStatus.BookingStatusName.CONFIRMED;
    private static final String STRIPE_PAID_STATUS = "paid";
    private static final String CURRENCY = "USD";
    private static final String PRODUCT_NAME = "Housing reservation";
    private static final String SUCCESS_URL = "http://localhost:8080/payments/success/";
    private static final String CANCEL_URL = "http://localhost:8080/payments/cancel/";

    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final PaymentMapper paymentMapper;
    private final MassageConfigurator massageConfigurator;
    private final BookingService bookingService;

    @Override
    @Transactional
    public String createPaymentCheckoutSession(Long id, String email) {
        Booking booking = getBookingById(id);
        checkBookingStatus(booking);
        checkBookingUser(email, booking);
        Optional<String> paymentUrlOptional = getUrlIfPaymentCreate(id);
        if (paymentUrlOptional.isPresent()) {
            return paymentUrlOptional.get();
        }
        SessionCreateParams sessionParams = getSessionParams(booking);
        Session session = getStripeSession(sessionParams);
        Payment payment = createPayment(session, booking);
        paymentRepository.save(payment);
        return session.getUrl();
    }

    private Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find booking with id: " + id));
    }

    private void checkBookingStatus(Booking booking) {
        if (!booking.getStatus().getName().equals(PENDING_BOOKING_STATUS)) {
            throw new CantPaidBookingException("Cant paid booking with id: " + booking.getId()
                    + " because booking status: " + booking.getStatus().getName());
        }
    }

    private void checkBookingUser(String email, Booking booking) {
        if (!email.equals(booking.getUser().getEmail())) {
            throw new UserDontHavePermissions("User with email: " + email
                    + " cant have permission to booking with id: " + booking.getId());
        }
    }

    private Optional<String> getUrlIfPaymentCreate(Long bookingId) {
        String url = null;
        Optional<Payment> optional = paymentRepository.findPaymentByBookingId(bookingId);
        if (optional.isPresent()) {
            Payment payment = optional.get();
            if (payment.getStatus().getName().equals(PENDING_PAYMENT_STATUS)) {
                url = payment.getSessionUrl();
            } else {
                throw new CantPaidBookingException("Can't pay booking with id: " + bookingId);
            }
        }
        return Optional.ofNullable(url);
    }

    private SessionCreateParams getSessionParams(Booking booking) {
        return new SessionCreateParams.Builder()
                .addPaymentMethodType(PAYMENT_METHOD)
                .addLineItem(getLineItem(booking))
                .setMode(SESSION_MODE)
                .setSuccessUrl(SUCCESS_URL + booking.getId())
                .setCancelUrl(CANCEL_URL + booking.getId())
                .build();
    }

    private SessionCreateParams.LineItem getLineItem(Booking booking) {
        return new SessionCreateParams.LineItem.Builder()
                .setPriceData(new SessionCreateParams.LineItem.PriceData.Builder()
                        .setCurrency(CURRENCY)
                        .setUnitAmount(getAmount(booking))
                        .setProductData(new SessionCreateParams
                                .LineItem.PriceData.ProductData.Builder()
                                .setName(PRODUCT_NAME)
                                .setDescription(massageConfigurator.toMassage(booking))
                                .build())
                        .build())
                .setQuantity(getQuantity(booking.getCheckDates()))
                .build();
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

    private Session getStripeSession(SessionCreateParams sessionParams) {
        try {
            return Session.create(sessionParams);
        } catch (StripeException e) {
            throw new StripeSessionException(
                    "Cant initialize Stripe payment session " + e.getMessage());
        }
    }

    private Payment createPayment(Session session, Booking booking) {
        Payment newPayment = new Payment();
        newPayment.setSessionUrl(session.getUrl());
        newPayment.setSessionId(session.getId());
        newPayment.setBooking(booking);
        newPayment.setAmount(BigDecimal.valueOf(session.getAmountTotal()));
        setStatusToPayment(newPayment, PENDING_PAYMENT_STATUS);
        return newPayment;
    }

    private void setStatusToPayment(Payment payment, PaymentStatus.PaymentStatusName statusName) {
        PaymentStatus paidStatus = paymentStatusRepository.findPaymentStatusByName(statusName);
        payment.setStatus(paidStatus);
    }

    @Override
    @Transactional
    public PaymentDto successPayment(Long bookingId, String email) {
        Payment payment = getPaymentByBookingId(bookingId);
        checkBookingUser(email, payment.getBooking());
        if (payment.getStatus().getName().equals(PAID_PAYMENT_STATUS)) {
            return paymentMapper.toDto(payment);
        }
        Session session = getSessionById(payment.getSessionId());
        checkSessionPaymentStatus(session.getPaymentStatus());
        updateBookingStatus(payment.getBooking(), CONFIRMED_BOOKING_STATUS);
        updatePaymentStatus(payment, PAID_PAYMENT_STATUS);
        return paymentMapper.toDto(payment);
    }

    private Payment getPaymentByBookingId(Long id) {
        return paymentRepository.findPaymentByBookingId(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find payment where booking id: " + id));
    }

    private Session getSessionById(String id) {
        try {
            return Session.retrieve(id);
        } catch (StripeException e) {
            throw new StripeSessionException("Problem with get stripe session: " + e.getMessage());
        }
    }

    private void checkSessionPaymentStatus(String paymentStatus) {
        if (!STRIPE_PAID_STATUS.equals(paymentStatus)) {
            throw new PaymentDontConfirmException("Current payment status is " + paymentStatus);
        }
    }

    private void updateBookingStatus(Booking booking, BookingStatus.BookingStatusName statusName) {
        BookingStatus bookingStatus
                = bookingStatusRepository.findBookingStatusByName(statusName);
        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);
    }

    private void updatePaymentStatus(Payment payment, PaymentStatus.PaymentStatusName statusName) {
        setStatusToPayment(payment, statusName);
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public PaymentDto cancelPaymentAndBooking(Long bookingId, String email) {
        checkBookingUser(email, getBookingById(bookingId));
        Payment payment = getPaymentByBookingId(bookingId);
        checkPaymentStatus(payment, PENDING_PAYMENT_STATUS);
        updatePaymentStatus(payment, CANCEL_PAYMENT_STATUS);
        bookingService.cancel(bookingId, email);
        return paymentMapper.toDto(payment);
    }

    private void checkPaymentStatus(Payment payment, PaymentStatus.PaymentStatusName statusName) {
        if (!payment.getStatus().getName().equals(statusName)) {
            throw new PaymentCancelException("Cant cancel payment with id: " + payment.getId()
                    + " because status: " + payment.getStatus().getName());
        }
    }
}
