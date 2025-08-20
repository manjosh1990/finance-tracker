package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.categories.Category;
import org.junit.jupiter.api.Test;

class CategoryControllerTest extends AbstractIT {

    @Test
    void testSaveCategory() {
        final Category input = new Category(null, "Test Category", "test-icon.png", "test-type", 1L, null, null);
        given().contentType("application/json")
                .body(input)
                .auth()
                .oauth2(getToken())
                .when()
                .post("/categories")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Category"))
                .body("id", notNullValue())
                .body("icon", equalTo("test-icon.png"))
                .body("type", equalTo("test-type"))
                .body("profileId", equalTo(1))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }
}
