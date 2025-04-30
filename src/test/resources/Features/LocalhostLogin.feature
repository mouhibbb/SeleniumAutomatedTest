Feature: feature to test login in localhost

  Scenario: Validate Login
    When user enter login and password
    And clicks on login
    Then user navigated to the home page
    And user clicks on "Users"
    And user activates all users in the table
    Then user is redirected to the new users page
    Then user activates all new bank accounts in the table
    Then user is redirected to the Credit users page
    And User activates all users in the table of Credit
    Then user clicks on Log out
