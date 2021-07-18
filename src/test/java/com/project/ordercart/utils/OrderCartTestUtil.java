package com.project.ordercart.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public class OrderCartTestUtil {

    public static ValidatableResponse createRequestPost(String url, Object body, HttpStatus httpStatus) {
        return RestAssured.given()
        .spec(requestSpecification(body))
        //.log()
        //.all()
        .when().
                post(url)
        .then().
                statusCode(httpStatus.value());
    }

    private static RequestSpecification requestSpecification(Object body) {
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("Accept", ContentType.JSON.toString())
                .setBody(body)
                .build();
    }
}