Feature: anonymous user can get post details by it's id

  @api
  Scenario Outline: Anyone can get post info by id
    Given I am a non-registered user
    When I request "<postsUrl>" with "<postId>"
    Then I get a response with success status code
    And I received the post title
    And I received the post body

    Examples:
      | postsUrl                                    | postId |
      | https://jsonplaceholder.typicode.com/posts/ | 1      |