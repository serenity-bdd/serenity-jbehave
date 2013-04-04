Meta:
@global shared

Scenario: A scenario with a local metadata field defined
Meta:
@local defined

Given I have an implemented JBehave scenario
And the scenario uses a custom tag
When I run the scenario
Then the local variable should be defined

Scenario: A scenario with no local metadata field defined

Given I have an implemented JBehave scenario
And the scenario uses the custom tag MY_OTHER_STORED_PROCEDURE
When I run the scenario
Then the local variable should not be defined
