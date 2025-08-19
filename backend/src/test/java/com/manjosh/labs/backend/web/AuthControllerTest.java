package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.authentication.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-users-data.sql")
class AuthControllerTest extends AbstractIT {

    @Test
    void testAuthenticate_shouldReturnToken() {
        final Auth auth = new Auth("valid@example.com", "securePassword123");
        given().contentType("application/json")
                .body(auth)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("message", equalTo("Login successful"));
    }
}
