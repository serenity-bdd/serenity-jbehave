Scenario: A scenario for UAT
Meta:
@environment uat

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: A scenario for TEST
Meta:
@environment test

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result

Scenario: A scenario for DEV
Meta:
@environment dev

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result