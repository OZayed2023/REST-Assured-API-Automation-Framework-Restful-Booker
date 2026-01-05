package com.restful.booker.apis;

import com.restful.booker.config.Specs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {

    private static RequestSpecification withAuth(String token) {
        RequestSpecification request = given()
                .spec(Specs.getRequestSpec());

        if (token != null && !token.isEmpty()) {
            request.cookie("token", token);
        }

        return request;
    }

    public static Response performPost(String path, Object body, String token) {
        return withAuth(token)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract().response();
    }

    public static Response performGet(String path, String token) {
        return withAuth(token)
                .when()
                .get(path)
                .then()
                .extract().response();
    }

    public static Response performPut(String path, Object body, String token) {
        return withAuth(token)
                .body(body)
                .when()
                .put(path)
                .then()
                .extract().response();
    }

    public static Response performDelete(String path, String token) {
        return withAuth(token)
                .when()
                .delete(path)
                .then()
                .extract().response();
    }
}
