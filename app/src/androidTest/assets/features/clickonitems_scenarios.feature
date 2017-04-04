Feature: I want to click on + and - button on different items
  @gono
  Scenario Outline: When the user tap + and - button sees the result changing
    Given I am on the main page
    Then I see the message list
    And I tap the plus button on item n.<ITEM1>
    And I tap the plus button on item n.<ITEM2>
    And I tap the plus button on item n.<ITEM3>
    And I tap the minus button on item n.<ITEM1>
    Then I should see the result <RESULT>



    Examples:
      | ITEM1  | ITEM2 | ITEM3 | RESULT |
      | "1" | "2" | "3" | "4,09" |
      | "3" | "3" | "3" | "3,98" |
      | "2" | "3" | "4" | "2,72" |