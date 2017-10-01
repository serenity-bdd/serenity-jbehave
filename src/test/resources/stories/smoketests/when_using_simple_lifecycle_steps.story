A scenario that uses lifecycle phases

Lifecycle:
Before:
Given I have a calculator
And I add 1

Scenario: Add a number
When I add 2
Then the total should be 3
