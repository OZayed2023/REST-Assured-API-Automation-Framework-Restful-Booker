package com.restful.booker.config;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    private static String getBaseUrl() {
        String env = System.getProperty("env", "PRODUCTION");
        return switch (env.toUpperCase()) {
            case "PRODUCTION" -> "https://restful-booker.herokuapp.com";
            case "LOCAL" -> "http://localhost:3001";
            default -> throw new RuntimeException("Environment is not supported: " + env);
        };
    }

    public static RequestSpecification getRequestSpec() {
        return given()
                .baseUri(getBaseUrl())
                .contentType(ContentType.JSON)
                .log().all();
    }
}
