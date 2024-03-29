package com.epam.gymtaskapplication.cucumberglue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Optional;

public class TraineeControllerSteps {

    private final String baseUrl = "http://localhost:8080/api"; // Update the base URL accordingly
    private Response response;

    @Given("a valid username {string} and password {string}")
    public void a_valid_username_and_password(String username, String password) {
        // No action needed here as we'll pass the username and password in the request body
    }

    @When("I send a GET request to {string}")
    public void i_send_a_GET_request_to(String endpoint) {
        response = RestAssured.get(baseUrl + endpoint);
    }

    @When("I send a POST request to {string} with body:")
    public void i_send_a_POST_request_to_with_body(String endpoint, String requestBody) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(baseUrl + endpoint);
    }

    @When("I send a DELETE request to {string} with body:")
    public void i_send_a_DELETE_request_to_with_body(String endpoint, String requestBody) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .delete(baseUrl + endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        Assert.assertEquals("Unexpected status code", Optional.ofNullable(expectedStatusCode), response.getStatusCode());
    }

    @Then("the response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response message does not contain expected message", responseBody.contains(expectedMessage));
    }
}