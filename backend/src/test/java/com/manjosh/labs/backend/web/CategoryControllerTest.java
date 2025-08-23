package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.categories.Category;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-categories-data.sql")
class CategoryControllerTest extends AbstractIT {

    @Test
    void testSaveCategory() {
        final Category input = createTestCategory("Test Category");
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

    private Category createTestCategory(final String testCategory) {
        return new Category(null, testCategory, "test-icon.png", "test-type", 1L, null, null);
    }

    @Test
    void testGetAllCategories() {
        given().auth()
                .oauth2(getToken())
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("size()", equalTo(5));
    }

    @Test
    void testGetCategoriesByType() {
        Response response = given().auth().oauth2(getToken()).when().get("/categories/EXPENSE");
        response.then().statusCode(200).body("size()", equalTo(3));
    }

    @Test
    void testUpdateCategories() {
        final ResponseBody output = given().contentType("application/json")
                .body(createTestCategory("My category"))
                .auth()
                .oauth2(getToken())
                .when()
                .post("/categories")
                .body();

        final Long id = output.jsonPath().getLong("id");
        final Category category =
                new Category(id, "My updated category", "updated-icon.png", "updated-type", 1L, null, null);

        given().contentType("application/json")
                .body(category)
                .auth()
                .oauth2(getToken())
                .when()
                .put("/categories")
                .then()
                .statusCode(200)
                .body("name", equalTo("My updated category"))
                .body("icon", equalTo("updated-icon.png"))
                .body("type", equalTo("updated-type"));
    }
}
