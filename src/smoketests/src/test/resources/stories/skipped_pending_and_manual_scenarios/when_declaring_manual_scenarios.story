Marking scenarios as 'manual' using metadata

Scenario: A manual scenario
Meta:
@tag expected-outcome:pending
@manual
Given I want to indicate that a scenario should be performed manually
When I tag it as @manual
Then it should be reported as manual pending

Scenario: A skipped manual scenario
Meta:
@tag expected-outcome:skip
@manual
@skip
Given I want to indicate that a scenario should be performed manually
And I also want it appearing in the skipped scenarios
When I tag it as @manual and @skipped
Then it should be reported as manual skipped


Scenario: A manual scenario scenario with undefined steps
Meta:
@tag expected-outcome:pending
@manual
Given I want to indicate that a scenario should be performed manually
When I tag it as @manual
And the steps are undefined
Then it should be reported as manual pending

