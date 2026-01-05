package com.restful.booker.apis;

import com.restful.booker.models.Booking;
import com.restful.booker.utils.Routes;
import io.restassured.response.Response;

public class BookingApi extends BaseApi {

    public static Response createBooking(Booking booking) {
        // Create booking without auth
        return performPost(Routes.BOOKINGS, booking, null);
    }

    public static Response getBookingById(int id) {
        String path = Routes.BOOKINGS + "/" + id;
        return performGet(path, null);
    }

    public static Response updateBooking(int id, Booking booking, String token) {
        String path = Routes.BOOKINGS + "/" + id;
        return performPut(path, booking, token);
    }

    public static Response deleteBooking(int id, String token) {
        String path = Routes.BOOKINGS + "/" + id;
        return performDelete(path, token);
    }
}
