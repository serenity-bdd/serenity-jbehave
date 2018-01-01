A scenario that uses lifecycle phases

Meta:
@ignore

GivenStories: preconditions/aPreconditionDuckDuckGo.story

Scenario: You can use GivenStories to run a whole story before starting a set of scenarios
When I lookup apple
Then I should see "apple at DuckDuckGo" in the page title

!-- Scenario: Look for lemons
!-- When I lookup lemons
!-- Then I should see "lemons at DuckDuckGo" in the page title
