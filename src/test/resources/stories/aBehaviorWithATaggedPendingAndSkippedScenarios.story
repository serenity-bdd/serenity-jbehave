Scenario: A scenario that works

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: A scenario that is pending
Meta:
@pending

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: A scenario that is work-in-progress
Meta:
@wip

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: scenario that is work-in-progress
Meta:
@skip

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: A scenario that is ignored
Meta:
@ignore

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result
