Feature: Make a coffee with a complete coffee machine
  As a user
  I want to use a coffee machine
  So that I can make coffee with different types and containers

  Scenario: A user plugs the coffee machine and makes an Arabica coffee in a mug
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "mug" with a capacity of 0.25 liters
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I add 0.5 liter of "ARABICA" in the bean tank
    And I make a coffee "ARABICA"
    Then the coffee machine returns a coffee "mug" not empty
    And the coffee volume equals to 0.25 liters
    And the coffee "mug" contains a coffee type "ARABICA"

  Scenario: A user plugs the coffee machine and makes a Robusta coffee in a cup
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "cup" with a capacity of 0.15 liters
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I add 0.5 liter of "ROBUSTA" in the bean tank
    And I make a coffee "ROBUSTA"
    Then the coffee machine returns a coffee "cup" not empty
    And the coffee volume equals to 0.15 liters
    And the coffee "cup" contains a coffee type "ROBUSTA"

  Scenario: A user tries to make a coffee without plugging the machine
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "mug" with a capacity of 0.25 liters
    When I add 1 liter of water in the water tank
    And I add 0.5 liter of "ARABICA" in the bean tank
    And I make a coffee "ARABICA"
    Then an error "You must plug your coffee machine." is thrown

  Scenario: A user tries to make a coffee with an empty bean tank
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "cup" with a capacity of 0.15 liters
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I make a coffee "ROBUSTA"
    Then an error "The bean tank is empty." is thrown

  Scenario: A user tries to make a coffee with an insufficient water level
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "mug" with a capacity of 0.25 liters
    When I plug the machine to electricity
    And I add 0.1 liter of water in the water tank
    And I add 0.5 liter of "ARABICA" in the bean tank
    And I make a coffee "ARABICA"
    Then an error "You must add more water in the water tank." is thrown

  Scenario: A user tries to make a coffee with a container that is not empty
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, and 600.0 l per hour of water flow for the pump
    And a "cup" with a capacity of 0.15 liters
    And the "cup" is not empty
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I add 0.5 liter of "ROBUSTA" in the bean tank
    And I make a coffee "ROBUSTA"
    Then an error "The container given is not empty." is thrown
