Scenario: A scenario that uses dates and times

Given I have a date 10/10/2000
And I have a list of dates 11/10/2000,12/10/2000
Given I have a joda date 10/10/2000
And I have a list of joda dates 11/10/2000,12/10/2000
And I have a time 15:00
And I have a list of times 16:00,17:00
When I run the story
Then the parameters should be converted