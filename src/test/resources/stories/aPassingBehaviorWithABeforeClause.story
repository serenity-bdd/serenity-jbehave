
Lifecycle:
Before:
Given a step that is executed before each scenario

After:
Outcome: ANY
Given a step that is executed after each scenario regardless of outcome

Scenario: A scenario that works

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result