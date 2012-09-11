Scenario: A scenario that runs slowly

Given I have an implemented JBehave scenario
And the scenario runs slowly
When I run the scenario
Then the test should time out