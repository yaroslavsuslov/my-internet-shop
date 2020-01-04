Feature: createAndDeleteProducts

  Scenario Outline: 02 - Create a product without a picture and and then remove it
    Given I open browser
    When I navigate to products.html page
    And click Create new button
    And I provide Product name as "<product_name>" and Price as "<price>" and Brand as "<brand>" and Categories as "<category>"
    And I click on Submit button
    Then product with name "<product_name>" is created
    When I delete product with name "<product_name>"
    Then product with name "<product_name>" is deleted
#    When Open dropdown menu
#    And click logout button
#    Then user logged out

    Examples:
      | product_name | price | brand | category |
      | test         | 1000  | Intel | Computers  |



