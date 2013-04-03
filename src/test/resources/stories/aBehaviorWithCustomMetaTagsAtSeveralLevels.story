Meta:
@global shared

Scenario: A scenario with a SQL tag
Meta:
@sql MY_STORED_PROCEDURE($param1, $param2)

Given I have an implemented JBehave scenario
And the scenario uses the custom tag MY_STORED_PROCEDURE
When I run the scenario
Then I should be able to read the custom tag MY_STORED_PROCEDURE and the global tag 'shared'

Scenario: A scenario with another SQL tag
Meta:
@sql MY_OTHER_STORED_PROCEDURE($param1, $param2)

Given I have an implemented JBehave scenario
And the scenario uses the custom tag MY_OTHER_STORED_PROCEDURE
When I run the scenario
Then I should be able to read the custom tag MY_OTHER_STORED_PROCEDURE and the global tag 'shared'

