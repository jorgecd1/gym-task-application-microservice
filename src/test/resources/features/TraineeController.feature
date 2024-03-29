Feature: Testing TraineeController Endpoints

  Scenario: Add a new trainee
    Given a new trainee with first name "John" and last name "Doe"
    When I send a POST request to "/api/trainee/add"
    Then the response status code should be 200
    And the response should contain the trainee's first name "John"
    And the response should contain the trainee's last name "Doe"

  Scenario: Get trainee information
    Given an existing trainee with username "johndoe" and password "password"
    When I send a GET request to "/api/trainee/get" with username "johndoe" and password "password"
    Then the response status code should be 200
    And the response should contain the trainee's username "johndoe"

  Scenario: Get not assigned trainers for a trainee
    Given an existing trainee with username "johndoe" and password "password"
    When I send a GET request to "/api/trainee/get-not-assigned" with username "johndoe" and password "password"
    Then the response status code should be 200
    And the response should contain a list of trainers

  Scenario: Update trainee information
    Given an existing trainee with username "johndoe" and password "password"
    And the trainee's first name is updated to "Jane"
    When I send a PUT request to "/api/trainee/update" with updated data
    Then the response status code should be 200
    And the response should contain the updated trainee's first name "Jane"

  Scenario: Delete trainee account
    Given an existing trainee with username "johndoe" and password "password"
    When I send a DELETE request to "/api/trainee/delete" with username "johndoe" and password "password"
    Then the response status code should be 200
    And the response message should indicate successful deletion