package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.Profile;
import com.manjosh.labs.backend.domain.ProfileService;
import com.manjosh.labs.backend.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class ProfileControllerTest extends AbstractIT {
    private final TestUtils testUtils = new TestUtils();
    private static final String BASE_URL = "src/test/resources/json/profile/";

    @Autowired
    private ProfileService profileService;

    @MockitoBean
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        profileService.deleteAllProfiles();
    }

    @Test
    void registerProfile() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
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

    @Test
    void activateProfile() {
        final Profile profile = testUtils.readJsonFromFile("profile_request.json", BASE_URL, Profile.class);
        final Profile savedProfile = profileService.registerProfile(profile, false);
        given().queryParam("token", savedProfile.activationCode())
                .when()
                .get("/activate")
                .then()
                .statusCode(200)
                .body(equalTo("Profile activated successfully"));
    }
}
