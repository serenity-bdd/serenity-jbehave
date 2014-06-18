Scenario: a scenario with embedded tables
Given that I sell the following fruit
| fruit  | price |
| apples | 5.00  |
| pears  | 6.00  |
And I sell the following vegetables
| vegetable | price |
| potatoe   | 4.00  |
| carrot    | 5.50  |
When I sell fruit
Then the total cost should be total
Examples:
| goods           | total |
| apples, carrot  | 11.50 |
| apples, pears   | 11.00 |
| potatoe, carrot | 9.50 |