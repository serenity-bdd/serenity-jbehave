GivenStories: stories/subset/aPreconditionToFile.story

Scenario: A scenario that uses selenium

GivenStories: stories/subset/aPreconditionToScenario1.story,
              stories/subset/aPreconditionToScenario2.story

Then I should see the Joe and Bloggs