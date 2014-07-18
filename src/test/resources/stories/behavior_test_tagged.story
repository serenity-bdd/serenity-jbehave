Meta:
@environment test
@speed fast
@driver phantomjs

Scenario: A scenario for TEST

Given I have an implemented JBehave scenario
And the scenario works
When I run the scenario
Then I should get a successful result
