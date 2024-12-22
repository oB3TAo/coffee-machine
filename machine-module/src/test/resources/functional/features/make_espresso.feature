Feature: Espresso Coffee Machine

  Scenario: Making an espresso with a properly set up coffee machine
    Given an espresso coffee machine with water and coffee beans
    And an empty coffee cup with capacity 0.2
    When I select the coffee type "ARABICA"
    And I make a coffee
    Then the coffee cup should contain coffee of type "ARABICA"

  Scenario: Attempting to make coffee without plugging in the machine
    Given an espresso coffee machine with water and coffee beans
    And an empty coffee cup with capacity 0.2
    And the machine is not plugged in
    When I select the coffee type "ARABICA"
    And I make a coffee
    Then an error should be thrown with message "You must plug your coffee machine."

  Scenario: Attempting to make coffee with insufficient water
    Given an espresso coffee machine with water and coffee beans
    And an empty coffee cup with capacity 0.2
    And the water tank has only 0.1 liters
    When I select the coffee type "ARABICA"
    And I make a coffee
    Then an error should be thrown with message "You must add more water in the water tank."

  Scenario: Attempting to make coffee with a non-empty cup
    Given an espresso coffee machine with water and coffee beans
    And a non-empty coffee cup with capacity 0.2
    When I select the coffee type "ARABICA"
    And I make a coffee
    Then an error should be thrown with message "The container given is not empty."
