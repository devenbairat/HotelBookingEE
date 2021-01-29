package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import browser.BaseUtil;
import pages.HotelBookingPage;
import utilities.Utilities;

import java.util.List;
import java.util.Map;

public class BookingStepDefinitions extends BaseUtil {

    BaseUtil base;
    private HotelBookingPage bookingPage;
    private String uniqueId;
    private List<Map<String, String>> bookingDetailsMap;

    public BookingStepDefinitions(BaseUtil base){
        super();
        this.base = base;
    }

    @Given("A user requests to book a room with following details:")
    public void enter_user_booking_details(DataTable bookingDetails) throws Exception {

        bookingPage       = new HotelBookingPage(base.getDriver());
        bookingDetailsMap = bookingDetails.asMaps(String.class, String.class);
        uniqueId          = Utilities.getUniqueId();

        bookingPage.navigateToBookingPage()
                   .captureBookingDetails(bookingDetailsMap, uniqueId);

    }

    @When("I confirm the booking for the user")
    public void save_user_booking() {
        bookingPage.confirmBooking();
    }

    @Then("the room should be booked on the system")
    public void validate_room_booked() throws Exception {
        bookingPage.validateBooking(bookingDetailsMap, uniqueId);
    }

    @Then("I should be able to cancel the booking")
    public void validate_booking_cancelled() {
        bookingPage.cancelBooking(uniqueId)
                   .confirmBookingDeleted(uniqueId);
    }

    @Then("the room should not be booked on the system")
    public void validate_room_not_booked() {
        bookingPage.confirmBookingNotSaved(uniqueId);
    }

}
