Feature: Testing Training Controller Endpoints

  Scenario: Adding a Training
    Given a valid trainee username "testtrainee" and trainer username "testtrainer"
    And a valid training name "Test Training"
    And a valid training date "2024-03-30" and duration "60"
    When I send a POST request to "/training/add" with body:
      """
      {
        "traineeUsername": "testtrainee",
        "trainerUsername": "testtrainer",
        "trainingName": "Test Training",
        "trainingDate": "2024-03-30",
        "trainingDuration": 60
      }
      """
    Then the response status code should be 200