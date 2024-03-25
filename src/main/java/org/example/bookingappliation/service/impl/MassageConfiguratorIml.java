package org.example.bookingappliation.service.impl;

import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.model.accommodation.Address;
import org.example.bookingappliation.model.booking.Booking;
import org.example.bookingappliation.model.booking.CheckDate;
import org.example.bookingappliation.model.user.User;
import org.example.bookingappliation.service.MassageConfigurator;
import org.springframework.stereotype.Service;

//this is temporary solution
@Service
public class MassageConfiguratorIml implements MassageConfigurator {
    private static final String HEADER = "Your booking: ";
    private static final String CHECK_DATE_HEADER = "\nCheck dates:";
    private static final String USER_HEADER = "\nUser:";
    private static final String ACCOMMODATION = "\nAccommodation: ";
    private static final String TAB = "\n     ";
    private static final String SEPARATOR = ", ";

    @Override
    public String toMassage(Booking booking) {
        User user = booking.getUser();
        CheckDate checkDate = booking.getCheckDates();
        Accommodation accommodation = booking.getAccommodation();
        return HEADER
                + getCheckDatesBlock(checkDate)
                + getUserBlock(user)
                + getAccommodationBlock(accommodation);
    }

    private String getCheckDatesBlock(CheckDate checkDate) {
        return CHECK_DATE_HEADER
                + TAB + "Check in date: " + checkDate.getCheckInDate()
                + TAB + "Check out date: " + checkDate.getCheckOutDate();
    }

    private String getUserBlock(User user) {
        return USER_HEADER
                + TAB + "First name: " + user.getFirstName()
                + TAB + "Last name: " + user.getLastName()
                + TAB + "Email: " + user.getEmail();
    }

    private String getAccommodationBlock(Accommodation accommodation) {
        return ACCOMMODATION
                + TAB + "Type: " + accommodation.getType().getName()
                + TAB + "Size: " + accommodation.getSize().getName()
                + TAB + "Daly rate: " + accommodation.getDailyRate()
                + TAB + "Address: " + getAddress(accommodation.getAddress());

    }

    private String getAddress(Address address) {
        return address.getCountry()
                + SEPARATOR + address.getState()
                + SEPARATOR + address.getState()
                + SEPARATOR + address.getCity()
                + SEPARATOR + address.getStreet()
                + SEPARATOR + address.getHouseNumber()
                + SEPARATOR + address.getZipCode();
    }
}
