Meta:
@driver phantomjs

Scenario: Another web scenario that uses selenium

Given I am on the test page
When I enter the first name Bob
Then I should see first name Bob on the screen

Scenario: Another web scenario that uses selenium v2

Given I am on the test page
When I enter the first name Mary
Then I should see first name Mary on the screen