package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.Profile;
import com.manjosh.labs.backend.utils.TestUtils;
import org.junit.jupiter.api.Test;

class ProfileControllerTest extends AbstractIT {
    private TestUtils testUtils = new TestUtils();
    private static final String BASE_URL = "src/test/resources/json/profile/";

    @Test
    void registerProfile() {
        final Profile profile = testUtils.readJsonFromFile("profile_request.json", BASE_URL, Profile.class);
        given().contentType("application/json")
                .body(profile)
                .when()
                .post("/register")
                .then()
                .statusCode(201)
                .body("fullName", equalTo("John Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .body("updatedAt", notNullValue())
                .body("createdAt", notNullValue());
    }
}
