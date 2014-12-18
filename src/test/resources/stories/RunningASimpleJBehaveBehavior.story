Scenario: Running a simple successful JBehave story

Given a JBehave story
When we run the story with Serenity
Then it should generate a Serenity report for this story
And it should throw an exception
