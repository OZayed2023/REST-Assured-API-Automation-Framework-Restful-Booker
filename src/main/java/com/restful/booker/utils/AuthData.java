package com.restful.booker.utils;

import com.restful.booker.models.AuthCredentials;

public class AuthData {

    public static AuthCredentials getAdminCredentials() {
        // Restful-booker
        return new AuthCredentials("admin", "password123");
    }
}
