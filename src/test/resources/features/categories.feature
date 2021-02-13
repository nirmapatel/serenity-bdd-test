Feature: Browse music categories

  Scenario Outline: Spotify categories endpoint positive case
    Given I authorize and received token
    When I browse categories with parameters
      | offset | <browseOffset> |
      | limit  | <browseLimit>  |
    Then Should receive response with status 200
    And Response should contain below values
      | categories.limit  | <browseLimit>  |
      | categories.offset | <browseOffset> |
    Examples:
      | browseOffset | browseLimit |
      | 0            | 20          |
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
      | 0            | 0           | Bad limit, limit must be larger than 0                |

    
    
  Scenario: Spotify categories endpoint without authentication
    When I browse categories with parameters and without token
      | offset | 0  |
      | limit  | 20 |
    Then Should receive response with status 401