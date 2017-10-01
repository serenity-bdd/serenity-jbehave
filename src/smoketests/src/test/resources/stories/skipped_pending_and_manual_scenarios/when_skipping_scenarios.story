Marking scenarios as pending, skipped or ignored

Scenario: A simple passing scenario
Meta:
@tag expected-outcome:success
Given I want to search for something
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: Skipping a scenario using the @skip annotation
Steps in the scenario will be reported as 'ignored'
Meta:
@skip
@tag expected-outcome:skip
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: Ignoring a scenario
You can also ignore an entire scenario, which is a bit like skipping it
Meta:
@ignore
@tag expected-outcome:ignore
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: You mark a scenario as pending using the @pending annotation
Pending scenarios are meant to indicate a scenario that has not been completed yet.
Meta:
@pending
@tag expected-outcome:pending
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: A scenario with no step definitions will be marked as pending by default
Meta:
@tag expected-outcome:pending
When I use a step that has no step definition
Then the step without a step definition should be pending
And subsequent steps should be ignored

Scenario: You can mark a scenario with no step definitions as @skipped
Meta:
@skip
@tag expected-outcome:skip
When I use a step that has no step definition
And I tag the scenario with @Skip
Then the overall result should be skipped
Then the steps without a step definition should be pending
And subsequent steps should be ignored
