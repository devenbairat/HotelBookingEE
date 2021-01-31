Feature: As a hotel receptionist
  I should be able to book a room for the user
  Provided I have valid details

  Scenario Outline: Book hotel room with deposit and other valid user details
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price | Deposit   | Check-in | Check-out |
      | Dev       | TestUser | 100   | <deposit> | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should be booked on the system
    Examples:
      | deposit |
      | true    |
      | false   |

  Scenario: Book hotel room with same checkIn and checkOut dates
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price  | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 150.99 | true    | today    | today     |
    When I confirm the booking for the user
    Then the room should be booked on the system

  Scenario: Book hotel room with checkOut date in future e.g., 30 days in future
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price   | Deposit | Check-in | Check-out     |
      | Dev       | TestUser | 1000.99 | true    | tomorrow | 30 days later |
    When I confirm the booking for the user
    Then the room should be booked on the system

  Scenario: Book hotel room with booking amount of less than 10 digits and with two decimals
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price        | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 100000000.99 | true    | today    | today     |
    When I confirm the booking for the user
    Then the room should be booked on the system

  Scenario: Booking should be saved with booking amount upto 2 decimal places when more than two decimals are entered
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price         | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 100000000.999 | true    | today    | today     |
    When I confirm the booking for the user
    And the price on the booking should be set to 2 decimal places
