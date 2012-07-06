Meta:
@driver htmlunit

Scenario: A scenario that uses selenium

Given I am on the test page
When I enter the first name <firstname>
And I enter the last name <lastname>
Then I should see <firstname> and <lastname> in the names fields

Examples:
|firstname|lastname|
|Joe      | Bloggs|
|John     | Doe   |