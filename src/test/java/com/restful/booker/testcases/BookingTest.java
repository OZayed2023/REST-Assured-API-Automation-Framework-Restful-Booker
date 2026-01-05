package com.restful.booker.testcases;

import com.restful.booker.apis.BookingApi;
import com.restful.booker.models.Booking;
import com.restful.booker.utils.BookingData;
import com.restful.booker.utils.TokenManager;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingTest extends BaseTest {

    @Test(description = "Should be able to create a booking successfully")
    public void shouldCreateBookingSuccessfully() {

        // Body
        Booking booking = BookingData.generateValidBooking();

        // API
        Response response = BookingApi.createBooking(booking);

        // Assert
        assertThat(response.getStatusCode(), is(200));

        Integer bookingId = response.then().extract().path("bookingid");
        assertThat(bookingId, is(notNullValue()));
        assertThat(bookingId, greaterThan(0));
    }

    @Test(description = "Should be able to get booking by id")
    public void shouldGetBookingById() {

        // Arrange
        Booking booking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        // Act
        Response getResponse = BookingApi.getBookingById(bookingId);

        // Assert
        assertThat(getResponse.statusCode(), is(200));

        String returnedFirstName = getResponse.then().extract().path("firstname");
        String returnedLastName = getResponse.then().extract().path("lastname");

        assertThat(returnedFirstName, equalTo(booking.getFirstname()));
        assertThat(returnedLastName, equalTo(booking.getLastname()));
    }

    @Test(description = "Should be able to update booking when authorized")
    public void shouldUpdateBookingWhenAuthorized() {

        // Arrange - create original booking
        Booking originalBooking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(originalBooking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        // Get token using TokenManager
        String token = TokenManager.getToken();

        // Prepare updated booking (change some fields)
        Booking updatedBooking = BookingData.generateValidBooking();

        // Act - update booking
        Response updateResponse = BookingApi.updateBooking(bookingId, updatedBooking, token);

        // Assert
        assertThat(updateResponse.getStatusCode(), is(200));

        String returnedFirstName = updateResponse.then().extract().path("firstname");
        String returnedLastName = updateResponse.then().extract().path("lastname");

        assertThat(returnedFirstName, equalTo(updatedBooking.getFirstname()));
        assertThat(returnedLastName, equalTo(updatedBooking.getLastname()));
    }

    @Test(description = "Should be able to delete booking when authorized")
    public void shouldDeleteBookingWhenAuthorized() {

        // Arrange - create a booking first
        Booking booking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        // Get token
        String token = TokenManager.getToken();

        // Act - delete booking
        Response deleteResponse = BookingApi.deleteBooking(bookingId, token);

        // Assert delete response
        assertThat(deleteResponse.getStatusCode(), is(201));

        // Try to get the deleted booking (expected 404)
        Response getResponse = BookingApi.getBookingById(bookingId);
        assertThat(getResponse.getStatusCode(), is(404));
    }

    @Test(description = "E2E: Create, update and delete booking successfully")
    public void E2EScenario() {

        // Arrange - create booking
        Booking initialBooking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(initialBooking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        assertThat(bookingId, is(notNullValue()));

        // Get token
        String token = TokenManager.getToken();

        // Update booking
        Booking updatedBooking = BookingData.generateValidBooking();
        Response updateResponse = BookingApi.updateBooking(bookingId, updatedBooking, token);
        assertThat(updateResponse.getStatusCode(), is(200));

        // Verify updated data
        String updatedFirstname = updateResponse.then().extract().path("firstname");
        assertThat(updatedFirstname, equalTo(updatedBooking.getFirstname()));

        // Delete booking
        Response deleteResponse = BookingApi.deleteBooking(bookingId, token);
        assertThat(deleteResponse.getStatusCode(), is(201));

        // Verify it's deleted
        Response getResponse = BookingApi.getBookingById(bookingId);
        assertThat(getResponse.getStatusCode(), is(404));
    }
}
