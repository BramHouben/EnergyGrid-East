Feature: Scenario
  As a user
  I want to know the factor based on the scenario
  So the simulation can calculate the factor

  Scenario: Add property
    Given I have a scenario
    When I add 1
    Then the result should be 1

  Scenario: Add some properties
    Given I have a scenario
    When I add 'test1' name and add 4 amount and add REMOVE_SOLAR_PARK scenarioType
    Then the result should be 'test1' and 4 and REMOVE_SOLAR_PARK
