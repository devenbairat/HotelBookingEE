package booking;

import io.cucumber.datatable.DataTable;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Booking {

    private String firstname;
    private String surname;
    private String price;
    private String deposit;
    private String checkinDate;
    private String checkoutDate;
    private String uniqueId;

    public Booking(String firstname, String surname, String price, String deposit, String checkinDate, String checkoutDate) {
        this.firstname = firstname;
        this.surname = surname;
        this.price = price;
        this.deposit = deposit;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.uniqueId = setUniqueId();
    }

    public String setUniqueId() {
        return Utilities.getUniqueId();
    }

    public String getFirstname() {
        return firstname == null ? "" : firstname;
    }

    public String getSurname() {
        return surname == null ? "" : surname+"_"+uniqueId;
    }

    public String getPrice() {
        return price == null ? "" : price;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getCheckoutDate() {
        return checkoutDate == null ? "" : checkoutDate;
    }

    public String getCheckinDate() {
        return checkinDate == null ? "" : checkinDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public static Booking getBookingObject(DataTable dataTable) {
        List<Map<String, String>> bookingDetailsMap = dataTable.asMaps(String.class, String.class);
        List<Booking> bookingList = new ArrayList<>();

        bookingDetailsMap.forEach(k -> bookingList.add(new Booking(k.get("Firstname"), k.get("Surname"), k.get("Price"), k.get("Deposit"), k.get("Check-in"), k.get("Check-out"))));
        return bookingList.get(0);
    }
}
