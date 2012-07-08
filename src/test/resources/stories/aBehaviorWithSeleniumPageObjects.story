Meta:
@driver htmlunit

Scenario: A scenario that uses selenium

Given I start on the test page
When I enter a first name <firstname>
And I enter a last name <lastname>
Then I should see the <firstname> and <lastname> in the names fields

Examples:
|firstname|lastname|
|Joe      | Bloggs|
|John     | Doe   |