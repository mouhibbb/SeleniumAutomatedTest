Feature: Test de navigation et remplissage de formulaire via WebSocket

  Scenario: Navigation vers une URL via WebSocket
    Given le WebSocket est connecté
    When je reçois un message de type "NAVIGATION" avec l URL "http://exemple.com"
    Then je navigue vers "http://exemple.com"
    And je récupère les champs du formulaire disponibles
    And j envoie les données du formulaire via WebSocket

  Scenario: Remplissage et soumission d un formulaire
    Given le WebSocket est connecté
    And je suis sur la page "http://exemple.com/form"
    When je reçois un message de type "FILL_FORM" avec les valeurs suivantes
    Then je remplis le champ "email" avec "test@mail.com"
    And je remplis le champ "date" avec "2024-05-12"
    And je clique sur le bouton "submit"
