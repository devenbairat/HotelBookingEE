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

  Scenario: Book hotel room and proceed with cancellation of booking
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price  | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 9999   | false   | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should be booked on the system
    And I should be able to cancel the booking

