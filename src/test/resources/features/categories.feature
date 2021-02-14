Feature: Browse music categories

  End point documentation is available at 'https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-categories'
  
  Scenario: Spotify categories endpoint should return 20 items with 0 offset
    Given I authorize and received token
    When I browse categories with parameters
      | country | NL |
    Then Should receive response with status 200
    And Response should contain below values
      | categories.limit  | 20 |
      | categories.offset | 0  |
    And Response should contains items less than or equals to total

    
  Scenario Outline: Spotify categories endpoint positive case
    Given I authorize and received token
    When I browse categories with parameters
      | offset | <browseOffset> |
      | limit  | <browseLimit>  |
    Then Should receive response with status 200
    And Response should contain below values
      | categories.limit  | <browseLimit>  |
      | categories.offset | <browseOffset> |
    And Response should contains items less than or equals to total
    Examples:
      | browseOffset | browseLimit |
#      Minimum limit value = 1
      | 0            | 1           | 
#      Maximum limit value = 50
      | 0            | 50          |
      | 20           | 10          |


  Scenario Outline: Spotify categories endpoint negative case
    Given I authorize and received token
    When I browse categories with parameters
      | offset | <browseOffset> |
      | limit  | <browseLimit>  |
    Then Should receive response with status 400
    And Response should contain below values
      | error.message | <message> |
    Examples:
      | browseOffset | browseLimit | message                                               |
      | -1           | 20          | Bad offset, offset must be greater than or equal to 0 |
#      Minimum limit value should not less than 1
      | 0            | 0           | Bad limit, limit must be larger than 0                |
#      Minimum limit value should not greater than 50
      | 0            | 51          | Invalid limit                                         |


  Scenario: Spotify categories endpoint without authentication
    When I browse categories with parameters and without token
      | country | NL |
    Then Should receive response with status 401