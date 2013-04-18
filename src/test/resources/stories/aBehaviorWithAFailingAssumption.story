
Scenario: A scenario that fails its precondition

Given I have a failing precondition
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: Another scenario that works

Given I have a passing precondition
And the scenario works
When I run the scenario
Then I should get a successful result