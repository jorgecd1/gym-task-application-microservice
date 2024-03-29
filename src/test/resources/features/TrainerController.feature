Feature: Testing Trainer Controller Endpoints

  Scenario: Adding a Trainer
    Given a valid username "testuser" and password "testpassword"
    When I send a POST request to "/trainer/add" with body:
      """
      {
        "firstName": "John",
        "lastName": "Doe",
        "specializationName": "Fitness"
      }
      """
    Then the response status code should be 200

  Scenario: Getting Trainer Details
    Given a valid username "testuser" and password "testpassword"
    When I send a GET request to "/trainer/get"
    Then the response status code should be 200

  Scenario: Updating Trainer Details
    Given a valid username "testuser" and password "testpassword"
    When I send a PUT request to "/trainer/update" with body:
      """
      {
        "username": "testuser",
        "password": "testpassword",
        "firstName": "UpdatedFirstName",
        "lastName": "UpdatedLastName",
        "specializationName": "UpdatedSpecialization"
      }
      """
    Then the response status code should be 200

  Scenario: Deleting a Trainer
    Given a valid username "testuser" and password "testpassword"
    When I send a DELETE request to "/trainer/delete" with body:
      """
      {
        "username": "testuser",
        "password": "testpassword"
      }
      """
    Then the response status code should be 200