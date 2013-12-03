Scenario: Convert date times
Given I want to convert string values to Joda DateTime objects
When I pass a DateTime parameter a value of <value>
Then the parameter should be converted to a DateTime with a value of <expectedValue>
Examples:
|value       | expectedValue |
|31-10-2013  | 2013-10-31    |
|31/10/2013  | 2013-10-31    |

Scenario: Convert date time lists
Given I want to convert a list of string values to Joda DateTime objects
When I pass a DateTime List parameter a value of <value>
Then the parameter should be converted to a list of DateTimes with values <expected>
Examples:
|value                   | expected                   |
|31-10-2013              | 2013-10-31                 |
|31/10/2013, 21-11-2013  | 2013-10-31, 2013-11-21     |

Scenario: Convert times
Given I want to convert string values to Joda LocalTime objects
When I pass a LocalTime parameter a value of <value>
Then the parameter should be converted to a LocalTime with a value of <expected>
Examples:
|value       | expected |
|10:46       | 10:46    |
|14:20       | 14:20    |

Scenario: Convert lists of times
Given I want to convert string values to Joda LocalTime objects
When I pass a LocalTime List parameter a value of <value>
Then the parameter should be converted to a list of LocalTimes with values <expected>
Examples:
|value       | expected        |
|10:46       | 10:46           |
|10:46,14:20 | 10:46, 14:20    |


Scenario: Should convert month-year values
Given I want to convert string values to Joda MonthYear objects
When I pass a YearMonth parameter a value of <value>
Then the parameter should be converted to a YearMonth with a value of <expectedValue>
Examples:
|value       | expectedValue |
|10-2013     | 2013-10       |
|10/2013     | 2013-10       |

Scenario: Should convert lists of month-year values
Given I want to convert string values to Joda MonthYear objects
When I pass a list of YearMonth parameter a value of <value>
Then the parameter should be converted to a list of YearMonth with a value of <expected>
Examples:
|value               | expected         |
|10-2013             | 2013-10          |
|10/2013,11/2013     | 2013-10,2013-11  |

Scenario: Should convert duration values
Given I want to convert string values to Joda Durations
When I pass a Duration parameter a value of <value>
Then the parameter should be converted to a Duration with the value <expected> seconds
Examples:
|value               | expected      |
|1 second            | 1             |
|2 seconds           | 2             |
|1 minute            | 60            |
|2 minutes           | 120           |
|1 hour              | 3600          |
|2 hours             | 7200          |
|1 day               | 86400         |
|2 days              | 172800        |


