Scenario: A scenario with a SQL tag
Meta:
@sql MY_STORED_PROCEDURE($param1, $param2)

Given I have an implemented JBehave scenario
And the scenario uses a custom tag
When I run the scenario
Then I should be able to read the custom tag