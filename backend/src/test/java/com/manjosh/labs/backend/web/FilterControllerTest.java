package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.FilteredInput;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/test-categories-data.sql", "/test-incomes-expense-data.sql"})
class FilterControllerTest extends AbstractIT {

    @Test
    void testFilter() {
        final FilteredInput input = new FilteredInput("income", null, null, null, null, "desc");
        final Response response = given().contentType("application/json")
                .body(input)
                .auth()
                .oauth2(getToken())
                .when()
                .post("/filter");
        response.then().statusCode(200);
        response.prettyPrint(); // Print response for debugging
        response.then().body("content.size()", equalTo(2));
    }
}
