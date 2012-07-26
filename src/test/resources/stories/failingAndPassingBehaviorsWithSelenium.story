Scenario: A failing scenario that uses selenium

Given I am on the test page
When I enter the first name Joe
And I enter the last name Smith
Then I should see Jack and Black in the names fields

Scenario: A passing scenario that uses selenium

Given I am on the test page
When I enter the first name Joe
And I enter the last name Smith
Then I should see Joe and Smith in the names fields
