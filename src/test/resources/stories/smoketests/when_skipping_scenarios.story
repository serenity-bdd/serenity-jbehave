Skipping and Pending scenarios using metadata

Scenario: 1 Skipping a scenario
Meta:
@skip
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 2 Running a scenario
Meta:
@driver htmlunit

Given I want to search for something
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: 3 A manual scenario
Meta:
@manual
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 4 An ignored scenario
Meta:
@ignore
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 5 Running another scenario
Meta:
@driver htmlunit

Given I want to search for something
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: 6 A pending scenario
Meta:
@pending
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 7 A scenario with no step definitions
Given I want to do something that is undefined
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 8 A scenario marked as skipped with no step definitions
Meta:
@skip
Given I want to do something that is undefined
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 9 A skipped manual scenario
Meta:
@manual
@skip
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title
