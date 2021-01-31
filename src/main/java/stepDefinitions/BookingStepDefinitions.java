package stepDefinitions;

import booking.Booking;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import browser.BaseUtil;
import pages.HotelBookingPage;
import utilities.Utilities;

import static booking.Booking.getBookingObject;

public class BookingStepDefinitions extends BaseUtil {

    BaseUtil base;
    private HotelBookingPage bookingPage;
    private Booking booking;

    public BookingStepDefinitions(BaseUtil base){
        super();
        this.base = base;
    }

    @Given("A user requests to book a room with following details:")
    public void enter_user_booking_details(DataTable bookingDetails) throws Exception {

        bookingPage  = new HotelBookingPage(base.getDriver());
        booking      = getBookingObject(bookingDetails);
        bookingPage.navigateToBookingPage()
                   .captureBookingDetails(booking);
    }

    @When("I confirm the booking for the user")
    public void save_user_booking() {
        bookingPage.confirmBooking();
    }

    @Then("the room should be booked on the system")
    public void validate_room_booked() throws Exception {
        bookingPage.validateBooking(booking);
    }

    @Then("the price on the booking should be set to 2 decimal places")
    public void validate_price_is_2_decimals() {

        bookingPage.validatePriceOnBooking(Utilities.setPriceTo2DecimalPlaces(booking.getPrice()), booking.getUniqueId());
    }

    @Then("I should be able to cancel the booking")
    public void validate_booking_cancelled() {
        bookingPage.cancelBooking(booking.getUniqueId())
                   .confirmBookingDeleted(booking.getUniqueId());
    }

    @Then("the room should not be booked on the system")
    public void validate_room_not_booked() {
        bookingPage.confirmBookingNotSaved(booking.getUniqueId());
    }

}
