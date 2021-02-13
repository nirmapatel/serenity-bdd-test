package com.patel.nirma.spotify.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.util.List;

public class SpotifySteps {

    private String token;
    private Response response;

    @Steps
    private AuthorizationStep authorizationStep;

    @Steps
    private CategoriesStep categoriesStep;

    @Given("I authorize and received token")
    public void getAuthToken() {
        token = authorizationStep.getAuthorizationResponse()
                .then()
              //  .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .get("access_token");
    }

    @When("I browse categories with parameters$")
    public void browseCategories(DataTable table) {
        response = categoriesStep.browseCategories(token, table.asLists());
    }
    
    @When("I browse categories with parameters and without token$")
    public void browseCategoriesWithoutToken(DataTable table) {
        response = categoriesStep.browseCategoriesWithoutToken(table.asLists());
    }
    
    @Then("Should receive response with status {int}")
    public void validateStatus(int status) {
        response.then()
             //   .log().all()
                .statusCode(status);
    }

    @And("Response should contain below values$")
    public void responseContainsBelowValues(DataTable table) {
        JsonPath jsonPath = response.then().extract().jsonPath();

        for (List<String> values : table.asLists()) {
            Object actual = jsonPath.get(values.get(0));
            String expected = values.get(1);

            if (actual instanceof Integer) {
                Assert.assertEquals(expected, ((Integer) actual).toString());
            } else {
                Assert.assertEquals(expected, actual);
            }
        }
    }

}
