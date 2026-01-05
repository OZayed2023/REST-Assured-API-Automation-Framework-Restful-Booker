package com.restful.booker.utils;

import com.restful.booker.models.Booking;
import com.restful.booker.models.BookingDates;
import net.datafaker.Faker;

import java.time.LocalDate;

public class BookingData {

    private static final Faker faker = new Faker();

    public static Booking generateValidBooking() {

        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        int totalprice = faker.number().numberBetween(50, 500);
        boolean depositpaid = true;

        LocalDate checkinDate = LocalDate.now().plusDays(1);
        LocalDate checkoutDate = checkinDate.plusDays(3);

        BookingDates bookingDates = new BookingDates(
                checkinDate.toString(),
                checkoutDate.toString()
        );

        String additionalneeds = "Breakfast";

        return new Booking(
                firstname,
                lastname,
                totalprice,
                depositpaid,
                bookingDates,
                additionalneeds
        );
    }
}
