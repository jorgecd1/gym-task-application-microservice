package com.epam.gymtaskapplication.cucumberglue;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

public class SpecializationControllerSteps {
    private Response response;

    @Given("a specialization name {string}")
    public void a_specialization_name(String specializationName) {
        // No action needed for this step
    }

    @When("I send a POST request to {string}")
    public void i_send_a_POST_request_to(String endpoint) {
        response = RestAssured.post(endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response should contain the specialization name {string}")
    public void the_response_should_contain_the_specialization_name(String expectedSpecializationName) {
        response.then().body("name", Matchers.equalTo(expectedSpecializationName));
    }
}
