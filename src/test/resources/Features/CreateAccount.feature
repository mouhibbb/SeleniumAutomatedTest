Feature: feature to test create account in localhost

  Scenario: Validate Create Account
    When user clicks on Creer_un_compte And enter nom prenom email mots de passe et comfimer le mot de passe 
    Then user navigated to the home page
