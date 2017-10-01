Lifecycle phases can be used to run steps before and after a scenario

Lifecycle:
Before:
Given I have a calculator
And I add 1
After:
Then the total should not be zero

Scenario: A scenario with before and after phases
Meta:
@tag expected-outcome:success
When I add 2
Then the total should be 3

Scenario: Another scenario with before and after phases
Meta:
@tag expected-outcome:success
When I add 3
Then the total should be 4
