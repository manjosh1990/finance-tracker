package com.manjosh.labs.backend.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.expense.Expense;
import com.manjosh.labs.backend.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/test-categories-data.sql", "/test-incomes-expense-data.sql"})
class IncomeControllerTest extends AbstractIT {
    private final TestUtils testUtils = new TestUtils();
    private static final String BASE_URL = "src/test/resources/json/income/";

    @Test
    void testSaveAddIncome() {
        Response response = given().contentType("application/json")
                .auth()
                .oauth2(getToken())
                .body(testUtils.readJsonFromFile("addIncome.json", BASE_URL, Expense.class))
                .when()
                .post("/incomes");
        response.prettyPrint();
        response.then().statusCode(201).body("name", equalTo("Salary"));
    }

    @Test
    void testGetIncomes() {
        Response response = given().auth().oauth2(getToken()).when().get("/incomes");
        response.prettyPrint();
        response.then().statusCode(200).body("size()", equalTo(2));
    }

    @Test
    void testDeleteIncome() {
        given().auth().oauth2(getToken()).when().delete("/incomes/13").then().statusCode(204);
    }
}
