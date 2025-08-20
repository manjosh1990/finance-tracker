package com.manjosh.labs.backend;

import static io.restassured.RestAssured.given;

import com.manjosh.labs.backend.domain.authentication.Auth;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import({TestcontainersConfiguration.class, NoOpEmailService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {
    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected String getToken() {
        final Auth auth = new Auth("admin@example.com", "admin");
        return given().contentType("application/json")
                .body(auth)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("token");
    }
}
