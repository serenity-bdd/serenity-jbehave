Scenario: Serenity displays scenario outlines as tables
Meta:
@tag expected-outcome:success
Given I have a calculator
When I add <amount>
Then the total should be <total>
Examples:
| amount | total |
| 1      | 1     |
| 2      | 3     |
| 3      | 6     |