Scenario: data-driven scenario 1

Given a stock of <symbol> and a threshold of <threshold>
When the stock is traded at <price>
Then the alert status should be <status>
 
Examples:     
|symbol|threshold|price|status|
|STK1|10.0|5.0|OFF|
|STK1|11.0|11.0|ON|
|STK1|12.0|12.0|ON|

Scenario: data-driven scenario 2

Given a stock of <symbol> and a threshold of <threshold>
When the stock is traded at <price>
Then the alert status should be <status>

Examples:
|symbol|threshold|price|status|
|STK2|13.0|5.0|OFF|
|STK2|14.0|11.0|ON|
|STK2|15.0|12.0|ON|
|STK2|65.0|12.0|ON|