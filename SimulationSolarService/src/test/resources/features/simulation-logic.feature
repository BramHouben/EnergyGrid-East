Feature: Calculation
  As a user
  I want to know the results of some calculations
  So the simulation returns realistic results

  Scenario: Add simulation
    Given a solarUnit
      | Coordinates                     | SolarPanelType   | NumberOfPanels |
      | { "x": 52.39138, "y": 5.36222 } | MONO_CRYSTALLINE | 2              |
    Given a productionExpectation
      | Kw   | LocalDateTime         |
      | 1500 | "2021-06-14T12:00:00" |
    When I add 5 amount
    Then the result should be 1800
