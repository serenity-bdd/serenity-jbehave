A scenario that uses lifecycle phases

GivenStories: preconditions/aPreconditionDuckDuckGo.story

Scenario: You can use GivenStories to run a whole story before starting a set of scenarios
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title
