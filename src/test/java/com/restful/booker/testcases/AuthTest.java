package com.restful.booker.testcases;

import com.restful.booker.apis.AuthApi;
import com.restful.booker.apis.BaseApi;
import com.restful.booker.models.AuthCredentials;
import com.restful.booker.models.TokenResponse;
import com.restful.booker.utils.AuthData;
import com.restful.booker.utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthTest extends BaseTest {

    @Test(description = "Should create token with valid admin credentials")
    public void shouldCreateTokenWithValidCredentials() {

        AuthCredentials credentials = AuthData.getAdminCredentials();

        TokenResponse tokenResponse = AuthApi.createToken(credentials);

        assertThat(tokenResponse.getToken(), is(notNullValue()));
        assertThat(tokenResponse.getToken().trim().length(), greaterThan(0));
    }

    @Test(description = "Should not authenticate with invalid credentials")
    public void shouldNotAuthenticateWithInvalidCredentials() {

        AuthCredentials invalidCredentials = new AuthCredentials("wrongUser", "wrongPass");

        Response response = BaseApi.performPost(Routes.AUTH, invalidCredentials, null);

        assertThat(response.getStatusCode(), is(200));

        String reason = response.then().extract().path("reason");
        assertThat(reason.toLowerCase(), containsString("bad credentials"));
    }
}
