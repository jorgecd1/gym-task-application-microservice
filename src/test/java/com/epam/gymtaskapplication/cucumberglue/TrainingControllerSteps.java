package com.epam.gymtaskapplication.cucumberglue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Optional;

public class TrainingControllerSteps {

    private final String baseUrl = "http://localhost:8080/api"; // Update the base URL accordingly
    private Response response;

    @Given("a valid trainee username {string} and trainer username {string}")
    public void a_valid_trainee_username_and_trainer_username(String traineeUsername, String trainerUsername) {
        // No action needed here as we'll pass the usernames in the request body
    }

    @Given("a valid training name {string}")
    public void a_valid_training_name(String trainingName) {
        // No action needed here as we'll pass the training name in the request body
    }

    @Given("a valid training date {string} and duration {int}")
    public void a_valid_training_date_and_duration(String trainingDate, Integer duration) {
        // No action needed here as we'll pass the training date and duration in the request body
    }

    @When("I send a POST request to {string} with body:")
    public void i_send_a_POST_request_to_with_body(String endpoint, String requestBody) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(baseUrl + endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        Assert.assertEquals("Unexpected status code", Optional.ofNullable(expectedStatusCode), response.getStatusCode());
    }
}
