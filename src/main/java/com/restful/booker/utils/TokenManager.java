package com.restful.booker.utils;

import com.restful.booker.apis.AuthApi;

public class TokenManager {

    private static String token;

    public static String getToken() {
        if (token == null || token.isEmpty()) {
            token = AuthApi.getToken(AuthData.getAdminCredentials());
        }
        return token;
    }
}
