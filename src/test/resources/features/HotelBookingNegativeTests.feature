Feature: As a hotel receptionist
  When I enter invalid booking details
  I should not be able to book a room for the user

  Scenario: Try to book hotel room with past checkInDate
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in  | Check-out |
      | Dev       | TestUser | 50    | false   | yesterday | today     |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with past checkOutDate
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in  | Check-out |
      | Dev       | TestUser | 50    | false   | yesterday | yesterday |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with checkInDate later than checkOutDate
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 50    | false   | today    | yesterday |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with booking amount of more than 9 digits and without decimals
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price      | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 1000000000 | true    | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with negative booking amount
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in | Check-out |
      | Dev       | TestUser | -50   | true    | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with alphanumeric value in booking amount
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in | Check-out |
      | Dev       | TestUser | xyz1  | true    | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with special characters in Firstname of the user
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit | Check-in | Check-out |
      | Dev£      | TestUser | 100.1 | true    | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario: Try to book hotel room with special characters in Surname of the user
    Given A user requests to book a room with following details:
      | Firstname | Surname   | Price | Deposit | Check-in | Check-out |
      | Dev       | Test!User | 100.1 | true    | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should not be booked on the system

  Scenario Outline: Try to book hotel room with blank values in input fields
    Given A user requests to book a room with following details:
      | Firstname   | Surname   | Price   | Deposit | Check-in   | Check-out   |
      | <Firstname> | <Surname> | <Price> | true    | <Check-in> | <Check-out> |
    When I confirm the booking for the user
    Then the room should not be booked on the system
    Examples:
      | Firstname | Surname  | Price | Check-in | Check-out |
      |           | TestUser | 100.1 | today    | tomorrow  |
      | Dev       |          | 100.1 | today    | tomorrow  |
      | Dev       | TestUser |       | today    | tomorrow  |
      | Dev       | TestUser | 100.1 |          | tomorrow  |
      | Dev       | TestUser | 100.1 | today    |           |

