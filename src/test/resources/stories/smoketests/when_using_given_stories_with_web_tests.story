A scenario that uses lifecycle phases

GivenStories: stories/precondition/aPreconditionDuckDuckGo.story

Scenario: Look for apples
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title
