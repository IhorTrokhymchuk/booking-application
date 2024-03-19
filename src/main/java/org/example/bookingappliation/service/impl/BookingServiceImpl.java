package org.example.bookingappliation.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.bookings.request.BookingRequestDto;
import org.example.bookingappliation.dto.bookings.responce.BookingDto;
import org.example.bookingappliation.exception.BookingInfoException;
import org.example.bookingappliation.exception.EntityAlreadyExistsException;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.exception.InvalidDateException;
import org.example.bookingappliation.exception.UserDontHavePermissions;
import org.example.bookingappliation.mapper.BookingMapper;
import org.example.bookingappliation.mapper.CheckDateMapper;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.booking.Booking;
import org.example.bookingappliation.model.booking.BookingStatus;
import org.example.bookingappliation.model.booking.CheckDate;
import org.example.bookingappliation.model.user.User;
import org.example.bookingappliation.repository.accommodation.AccommodationRepository;
import org.example.bookingappliation.repository.booking.BookingRepository;
import org.example.bookingappliation.repository.bookingstatus.BookingStatusRepository;
import org.example.bookingappliation.repository.user.UserRepository;
import org.example.bookingappliation.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final Long DONT_HAVE_AVAILABLE_VALUE = 0L;
    private static final Long HAVE_ONE_AVAILABLE_VALUE = 1L;
    private static final BookingStatus.BookingStatusName PENDING_STATUS
            = BookingStatus.BookingStatusName.PENDING;
    private static final BookingStatus.BookingStatusName CANCELED_STATUS
            = BookingStatus.BookingStatusName.CANCELED;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final BookingMapper bookingMapper;
    private final CheckDateMapper checkDateMapper;

    @Override
    @Transactional
    public BookingDto save(BookingRequestDto requestDto, String email) {
        Booking booking = bookingMapper.toModelWithoutStatusAndUser(requestDto);
        isCheckDateValid(booking.getCheckDates());
        setAccommodationIfIsFree(booking, requestDto);
        setUserToBooking(booking, email);
        setBookingStatusToBooking(booking, PENDING_STATUS);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(savedBooking);
    }

    @Override
    @Transactional
    public BookingDto cancel(Long id, String email) {
        Booking booking = getByIdIfUserHavePermission(id, email);
        checkBookingStatus(booking, PENDING_STATUS);
        setBookingStatusToBooking(booking, BookingStatus.BookingStatusName.CANCELED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDto updateInfo(Long id, BookingRequestDto requestDto, String email) {
        Booking booking = getByIdIfUserHavePermission(id, email);
        CheckDate checkDate = checkDateMapper.toModel(requestDto.getCheckDates());
        isCheckDateValid(checkDate);
        checkBookingStatus(booking, PENDING_STATUS);
        isAccommodationFree(requestDto.getAccommodationId(), checkDate, HAVE_ONE_AVAILABLE_VALUE);
        bookingMapper.setUpdateInfoToBooking(booking, requestDto);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDto getById(Long id, String email) {
        return bookingMapper.toDto(getByIdIfUserHavePermission(id, email));
    }

    @Override
    @Transactional
    public List<BookingDto> getByUserEmail(String email, Pageable pageable) {
        List<Booking> allByUserEmail = bookingRepository.findAllByUser_Email(email, pageable);
        if (allByUserEmail.isEmpty()) {
            throw new EntityNotFoundException("User with email: " + email + " dont have bookings");
        }
        return allByUserEmail.stream().map(bookingMapper::toDto).toList();
    }

    private void checkBookingStatus(Booking booking, BookingStatus.BookingStatusName statusName) {
        BookingStatus bookingStatus = booking.getStatus();
        if (!bookingStatus.getName().equals(statusName)) {
            throw new BookingInfoException("Booking expected status: " + statusName
                    + " actual status: " + bookingStatus);
        }
    }

    private Booking getByIdIfUserHavePermission(Long id, String email) {
        Booking booking = getBookingById(id);
        if (!email.equals(booking.getUser().getEmail())) {
            throw new UserDontHavePermissions("User with email: " + email
                    + " cant see booking with id: " + id);
        }
        return booking;
    }

    private void setBookingStatusToBooking(Booking booking,
                                           BookingStatus.BookingStatusName statusName) {
        BookingStatus bookingPendingStatus
                = bookingStatusRepository.findBookingStatusByName(statusName);
        booking.setStatus(bookingPendingStatus);
    }

    private void setUserToBooking(Booking booking, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Cant find user with email: " + email));
        booking.setUser(user);
    }

    private void setAccommodationIfIsFree(Booking booking, BookingRequestDto requestDto) {
        Accommodation accommodation = getAccommodationById(requestDto.getAccommodationId());
        CheckDate checkDates = checkDateMapper.toModel(requestDto.getCheckDates());
        isAccommodationFree(requestDto.getAccommodationId(), checkDates, DONT_HAVE_AVAILABLE_VALUE);
        booking.setAccommodation(accommodation);
    }

    private void isAccommodationFree(Long id, CheckDate checkDate, Long availableValue) {
        Long available = bookingRepository.isDatesAvailableForAccommodation(
                id, checkDate.getCheckInDate(), checkDate.getCheckOutDate());

        if (available > availableValue) {
            throw new EntityAlreadyExistsException("Can't create booking where"
                    + " accommodationId: " + id
                    + " checkDates: " + checkDate);
        }
    }

    private Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find booking where id: " + id));
    }

    private Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find accommodation where id: " + id));
    }

    private void isCheckDateValid(CheckDate checkDates) {
        LocalDate checkInDate = checkDates.getCheckInDate();
        LocalDate checkOutDate = checkDates.getCheckOutDate();

        if (checkInDate.equals(checkOutDate) || checkInDate.isAfter(checkOutDate)) {
            throw new InvalidDateException("Invalid check in date ");
        }
    }
}
