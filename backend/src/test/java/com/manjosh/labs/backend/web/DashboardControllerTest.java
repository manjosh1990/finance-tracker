package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.manjosh.labs.backend.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/test-categories-data.sql", "/test-incomes-expense-data.sql"})
class DashboardControllerTest extends AbstractIT {

    @Test
    void testGetDashboardData() {
        Response response = given().contentType("application/json")
                .auth()
                .oauth2(getToken())
                .when()
                .get("/dashboard");
        response.prettyPrint();
        response.then().statusCode(200).body("totalBalance", equalTo(84500.0F));
    }
}
