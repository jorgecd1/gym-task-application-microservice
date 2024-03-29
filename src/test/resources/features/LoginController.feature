Feature: Testing LoginController endpoints

  Scenario: Valid login request
    Given a valid username "testUser" and password "testPassword"
    When I send a GET request to "/api/login"
    Then the response status code should be 200
    And the response message should be "Success! User is now logged in"

  Scenario: Invalid login request with missing credentials
    Given a missing username or password
    When I send a GET request to "/api/login"
    Then the response status code should be 400
    And the response message should be "Missing password or username"

  Scenario: Invalid login request with incorrect credentials
    Given an incorrect username "invalidUser" or password "invalidPassword"
    When I send a GET request to "/api/login"
    Then the response status code should be 401
    And the response message should be "Incorrect password or username"

  Scenario: Valid change-login request
    Given a valid username "testUser", old password "oldPassword", and new password "newPassword"
    When I send a PUT request to "/api/change-login"
    Then the response status code should be 200
    And the response message should be "Password was changed for this User"

  Scenario: Invalid change-login request with missing credentials
    Given a missing username or old password
    When I send a PUT request to "/api/change-login"
    Then the response status code should be 400
    And the response message should be "Missing password or username"

  Scenario: Invalid change-login request with missing new password
    Given a valid username "testUser" and old password "oldPassword", but missing new password
    When I send a PUT request to "/api/change-login"
    Then the response status code should be 400
    And the response message should be "Missing new password"

  Scenario: Invalid change-login request with incorrect credentials
    Given an incorrect username "invalidUser", old password "invalidPassword", and new password "newPassword"
    When I send a PUT request to "/api/change-login"
    Then the response status code should be 401
    And the response message should be "Password unchanged, incorrect password"