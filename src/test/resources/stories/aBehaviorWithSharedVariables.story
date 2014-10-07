A story with variable shared between normal step definitions and fixture methods

Scenario: First scenario

When a field is initialized to 1
Then it should have a value of 1

Scenario: Second scenario

When the same field is incremented
Then it should have a value of 2