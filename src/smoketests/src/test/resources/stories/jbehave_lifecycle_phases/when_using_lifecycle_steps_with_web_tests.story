Lifecycle phases also work with web tests

Lifecycle:
Before: Open the search page
Given I want to search for something

After: Check that some results are displayed
Then I should see search results

Scenario: Look for pears
When I lookup pear
Then I should see "pear at DuckDuckGo" in the page title

Scenario: Look for apples
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title
