Meta: @ignore

Scenario: Serenity automatically instantiates step library fields in step definition classes
Meta:
@tag expected-outcome:success
Given I want to use a step library
When I use a step library field annotated with @Steps
Then Serenity should instantiate the field

Scenario: Serenity instantiates different step libraries for each field by default
Meta:
@tag expected-outcome:success
Given I want to use several step library fields of the same type
When I use a step library fields to each of them
Then Serenity should instantiate a different library for each field

Scenario: Serenity creates new step library instances for each new scenario
Meta:
@tag expected-outcome:success
Given I have a Serenity step library
When I start a new scenario
Then the step library should be reinitialised

Scenario: You can share step library instances using @Steps(shared=true)
Meta:
@tag expected-outcome:success
Given I have two Serenity step libraries
When they are annotated with @Steps(shared=true)
Then both should refer to the same instance
