Feature: As a user i can order from the Shop

  @ui
  Scenario Outline: Verify the user cannot order with empty mandatory data
    Given I am a non-registered customer
    And I navigate to "<homePageUrl>"
    When I select english
    And I Select "<brand>"
    And I select the first results item
    And I press add to basket button
    And I press proceed to checkout button
    And I select the delivery city "<City>"
    And I select the delivery area "<Area>"
    And I select deliver to my address
    And I entered address details as "<Street>" "<BuildingNo>" "<FloorNo>" "<AprtNo>"
    And I press continue in delivery option section
    And I keep personal info empty
    And I press continue in personal info section
    Then I get an error on the mandatory empty fields

    Examples:
      | homePageUrl                              | brand  | City    | Area    | Street | BuildingNo | FloorNo | AprtNo |
      | https://eshop.vodafone.com.eg/shop/home  | Apple  | القاهرة     | عين شمس   | Thawra | B23        | 2       | 2      |