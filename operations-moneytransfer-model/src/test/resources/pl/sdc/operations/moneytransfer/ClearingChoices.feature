Feature: Clearing Choices

  Domain Story:

  possible Clearing Choices and priorities are configurable by bank
  money transfers inside single bank* doesn't require clearing
  time between 13:30 - 19:30 exclude "Same day transfer" Clearing Choice
  time span 13:30 - 19:30 can be configured by bank

  if there is not clearing required or there is only one option of clearing
  then customer doesn't have available Clearing Choice
  and proper one (or none) will be used implicitly

  if there is more then one available Clearing Choices
  then customer see all available Clearing Choices and one with highest priority is suggested

  picked Clearing Choice need to be one of available Clearing Choices

  *transfers inside single bank - debitor and credit accounts are from single bank

  info:
  - configuration is not changing often

  Scenario: Transfer inside single bank
    Given Transfer inside single bank
    When we check available Clearing Choices
    Then there is no option presented for user
    And no clearing is needed

  Scenario: bank in Sweden
    Given bank in Sweden
    And priorities of possible options are:
      | name              | coreValue |
      | Standard Transfer |           |
    When we check available Clearing Choices
    Then there is no option presented for user
    And no clearing will be defined while execution
    And "Standard Transfer" clearing will be used

  Scenario: Other than Denmark or Faroe Islands
    Given bank is not a Denmark or Faroe Islands bank
    When we check available Clearing Choices
    Then there is no option presented for user
    And "Standard Transfer" clearing will be used

  Scenario: Transfer for future date in Denmark or Faroe Islands
    Given Denmark or Faroe Islands
    And Transfer for future date
    When we check available Clearing Choices
    Then there is no option presented for user
    And "Standard Transfer" clearing will be used

  Scenario: Transfer for current date in Denmark or Faroe Islands
    Given Denmark or Faroe Islands
    And Transfer is not internal inside one bank
    And Transfer for today
    And current time is between 13:30 - 19:30 Danish time
    And priorities of possible options are:
      | name               | coreValue |
      | Standard Transfer  | 001       |
      | Same day transfer  | 002       |
      | Real Time Transfer | 003       |
    When we check available Clearing Choices
    Then we have "Standard Transfer", "Real Time Transfer" options
    And suggested one will be "Standard Transfer"

  Scenario: Limited bank options
    Given Transfer is not internal inside one bank
    And Transfer for today
    And current time outside time span 13:30 - 19:30 Danish time
    And priorities of possible options are:
      | name               | coreValue |
      | Standard Transfer  | 001       |
      | Real Time Transfer | 003       |
    When we check available Clearing Choices
    Then we have "Standard Transfer", "Real Time Transfer" options
    And suggested one will be "Standard Transfer"

  Scenario: Transfer for current date in Denmark or Faroe Islands
    Given Denmark or Faroe Islands
    And Transfer is not internal inside one bank
    And priorities of possible options are:
      | name               | coreValue |
      | Standard Transfer  | 001       |
      | Same day transfer  | 002       |
      | Real Time Transfer | 003       |
    And Transfer for today
    And current time outside time span 13:30 - 19:30 of Danish time
    When we check available Clearing Choices
    Then we have "Standard Transfer", "Same day transfer", "Real Time Transfer"
    And suggested one will be "Standard Transfer"

  Scenario: Express transfer enabled
  Scenario: