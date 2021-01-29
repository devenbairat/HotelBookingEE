Feature: As a hotel receptionist
  I should be able to cancel a booking for the user
  Provided the booking exists on the system

  Scenario: Book hotel room and proceed with cancellation of booking
    Given A user requests to book a room with following details:
      | Firstname | Surname  | Price  | Deposit | Check-in | Check-out |
      | Dev       | TestUser | 9999   | false   | today    | tomorrow  |
    When I confirm the booking for the user
    Then the room should be booked on the system
    And I should be able to cancel the booking

