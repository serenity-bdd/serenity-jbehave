Lifecycle phases also work with web tests

Meta:
@driver chrome

Lifecycle:
Before: Open the search page
Given I want to search for something

After: Check that some results are displayed
Then I should see search results

Scenario: Look for pears
When I lookup pear
Then I should see "DuckDuckGo" in the page title

Scenario: Look for apples
When I lookup apple
Then I should see "DuckDuckGo" in the page title


Scenario: Look for oranges
When I lookup oranges
Then I should see "DuckDuckGo" in the page title

