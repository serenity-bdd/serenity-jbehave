Skipping and Pending scenarios using metadata

Scenario: 1 Skipping a scenario
Meta:
@skip
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 2 Running a scenario
Given I want to search for something
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: 3 A manual scenario
Meta:
@manual
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

Scenario: 4 Running another scenario
Given I want to search for something
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: 5 A pending scenario
Meta:
@pending
Given I want to search for something
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title
