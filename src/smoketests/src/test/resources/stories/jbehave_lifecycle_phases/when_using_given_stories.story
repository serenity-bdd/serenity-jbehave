A scenario that uses lifecycle phases

GivenStories: preconditions/aPreconditionCalculator.story

Scenario: You can use GivenStories to run a whole story before starting a set of scenarios
Meta:
@current

When I add 2
Then the total should be 3

Scenario: The GivenStories story is only run once before the story, but shared instances are conserved between scenarios in a story
Meta:
@current

When I add 5
Then the total should be 8
