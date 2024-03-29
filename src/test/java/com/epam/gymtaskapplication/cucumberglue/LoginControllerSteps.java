package com.epam.gymtaskapplication.cucumberglue;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.Optional;

public class LoginControllerSteps {
    private String baseUrl = "http://localhost:8080/api"; // Update the base URL accordingly

    private Response response;

    @Given("a valid username {string} and password {string}")
    public void a_valid_username_and_password(String username, String password) {
        // No action needed here as we'll pass the username and password in the request body
    }

    @When("I send a GET request to {string}")
    public void i_send_a_GET_request_to(String endpoint) {
        response = RestAssured.get(baseUrl + endpoint);
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
