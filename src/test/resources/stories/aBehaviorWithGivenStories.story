GivenStories: stories/precondition/aPreconditionToFile.story

Scenario: A scenario that uses selenium

GivenStories: stories/precondition/aPreconditionToScenario1.story,
              stories/precondition/aPreconditionToScenario2.story

Then I should see the Joe and Bloggs