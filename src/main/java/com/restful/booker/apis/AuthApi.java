package com.restful.booker.apis;

import com.restful.booker.models.AuthCredentials;
import com.restful.booker.models.TokenResponse;
import com.restful.booker.utils.Routes;

public class AuthApi extends BaseApi {

    public static TokenResponse createToken(AuthCredentials credentials) {

        return performPost(Routes.AUTH, credentials, null)
                .then()
                .statusCode(200)
                .extract()
                .as(TokenResponse.class);
    }

    public static String getToken(AuthCredentials credentials) {
        return createToken(credentials).getToken();
    }
}
