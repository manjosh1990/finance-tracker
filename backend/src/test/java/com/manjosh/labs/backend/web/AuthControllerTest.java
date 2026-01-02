package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.authentication.Auth;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-users-data.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest extends AbstractIT {

    @Test
    @Order(1000) // High value to ensure this runs last
    void testAuthenticate_shouldReturnToken() {
        final Auth auth = new Auth("valid@example.com", "securePassword123");
        // perform request and capture response
        Response response =
                given().contentType("application/json").body(auth).when().post("/login");

        // assertions on response
        response.then().statusCode(200).body("message", equalTo("Login successful"));

        // extract values from response
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Token should be present in the response");
    }
}
