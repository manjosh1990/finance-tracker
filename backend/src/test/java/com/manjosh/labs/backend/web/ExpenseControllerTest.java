package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.expense.Expense;
import com.manjosh.labs.backend.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-categories-data.sql")
class ExpenseControllerTest extends AbstractIT {
    private final TestUtils testUtils = new TestUtils();
    private static final String BASE_URL = "src/test/resources/json/expense/";

    @Test
    void testSaveExpense() {
        given().contentType("application/json")
                .auth()
                .oauth2(getToken())
                .body(testUtils.readJsonFromFile("addExpense.json", BASE_URL, Expense.class))
                .when()
                .post("/expenses")
                .then()
                .statusCode(201)
                .body("name", equalTo("Groceries"));
    }
}
