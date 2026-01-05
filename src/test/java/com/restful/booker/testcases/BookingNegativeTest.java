package com.restful.booker.testcases;

import com.restful.booker.apis.BookingApi;
import com.restful.booker.models.Booking;
import com.restful.booker.models.BookingDates;
import com.restful.booker.utils.BookingData;
import com.restful.booker.utils.TokenManager;
import com.restful.booker.config.Specs;
import com.restful.booker.utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingNegativeTest extends BaseTest {

    // ========== NEGATIVE SCENARIOS ==========

    @Test(description = "Should return 404 when booking id does not exist")
    public void shouldReturnNotFoundForNonExistingBooking() {

        int nonExistingId = 999999999;

        Response response = BookingApi.getBookingById(nonExistingId);

        assertThat(response.getStatusCode(), is(404));
    }

    @Test(description = "Should not update booking without token (unauthorized)")
    public void shouldNotUpdateBookingWithoutToken() {

        Booking booking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        Booking updatedBooking = BookingData.generateValidBooking();
        Response updateResponse = BookingApi.updateBooking(bookingId, updatedBooking, null);

        assertThat(updateResponse.getStatusCode(), is(403));
    }


    @Test(description = "Should not delete booking without token (unauthorized)")
    public void shouldNotDeleteBookingWithoutToken() {

        Booking booking = BookingData.generateValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        Integer bookingId = createResponse.then().extract().path("bookingid");

        Response deleteResponse = BookingApi.deleteBooking(bookingId, null);

        assertThat(deleteResponse.getStatusCode(), is(403));
    }

    // ========== BUG-BASED TESTS ==========

    @Test(description = "BUG: System should return 4xx when deleting non-existing booking, not 5xx")
    public void bug_ShouldReturnClientError() {

        int nonExistingId = 999999999;
        String token = TokenManager.getToken();

        Response response = BookingApi.deleteBooking(nonExistingId, token);

        assertThat(
                "Expected a 4xx client error when deleting non-existing booking, but got: " + response.getStatusCode(),
                response.getStatusCode(),
                is(oneOf(400, 404))
        );
    }

    @Test(description = "BUG: totalprice should not accept text value")
    public void bug_ShouldNotAcceptTextInTotalPrice() {

        String invalidBody = "{\n" +
                "  \"firstname\" : \"Omar\",\n" +
                "  \"lastname\" : \"Zayed\",\n" +
                "  \"totalprice\" : \"abc\",\n" +
                "  \"depositpaid\" : true,\n" +
                "  \"bookingdates\" : {\n" +
                "    \"checkin\" : \"2025-01-01\",\n" +
                "    \"checkout\" : \"2025-01-05\"\n" +
                "  },\n" +
                "  \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Response response = given()
                .spec(Specs.getRequestSpec())
                .body(invalidBody)
                .when()
                .post(Routes.BOOKINGS)
                .then()
                .extract().response();

        assertThat(
                "Expected 400 when totalprice is text, but got: " + response.getStatusCode(),
                response.getStatusCode(),
                is(400)
        );
    }

    @Test(description = "BUG: firstname should not be allowed to be empty")
    public void bug_ShouldNotAllowEmptyFirstName() {

        BookingDates dates = new BookingDates(
                LocalDate.now().plusDays(1).toString(),
                LocalDate.now().plusDays(3).toString()
        );

        Booking bookingWithEmptyFirstName = new Booking(
                "",
                "Zayed",
                150,
                true,
                dates,
                "Breakfast"
        );

        Response response = BookingApi.createBooking(bookingWithEmptyFirstName);

        assertThat(
                "Expected 400 when firstname is empty, but got: " + response.getStatusCode(),
                response.getStatusCode(),
                is(400)
        );
    }
}
