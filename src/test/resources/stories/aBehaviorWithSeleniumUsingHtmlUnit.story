Scenario: A scenario that uses selenium

Given I am on the test page
When I enter the first name <firstname>
And I enter the last name <lastname>
Then I should see <firstname> and <lastname> in the names fields
And I should be using HtmlUnit

Examples:
|firstname|lastname|
|Joe      | Black|
|John     | Doe   |