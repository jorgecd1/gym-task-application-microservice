Feature: Testing SpecializationController Endpoints

  Scenario: Add a new specialization
    Given a specialization name "Cardiology"
    When I send a POST request to "/api/specialization/add/Cardiology"
    Then the response status code should be 200
    And the response should contain the specialization name "Cardiology"

  Scenario: Get an existing specialization
    Given an existing specialization name "Dermatology"
    When I send a GET request to "/api/specialization/get/Dermatology"
    Then the response status code should be 200
    And the response should contain the specialization name "Dermatology"

  Scenario: Get a non-existing specialization
    Given a non-existing specialization name "Orthopedics"
    When I send a GET request to "/api/specialization/get/Orthopedics"
    Then the response status code should be 404