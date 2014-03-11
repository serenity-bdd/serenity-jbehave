Scenario: A scenario that uses Spring

Given I hava an autowired Spring bean
When I use the bean
Then it should be instanciated

Given I hava a nested autowired Spring bean
When I use the nested bean
Then the nested bean should be instanciated