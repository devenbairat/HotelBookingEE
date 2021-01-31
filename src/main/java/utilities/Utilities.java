package utilities;

import booking.Booking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import io.cucumber.datatable.DataTable;

public final class Utilities {

    public static String getUniqueId() {
        Date dNow = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyMMddhhmmssMs");
        return formattedDate.format(dNow);
    }

    public static String setPriceTo2DecimalPlaces(String price) {
        if (price.contains(".")) {
            DecimalFormat df = new DecimalFormat("#.00");
            df.setRoundingMode(RoundingMode.DOWN);
            String str = df.format(Double.valueOf(price));
            return str;
        } else {
            return price;
        }
    }

    public static String getDates(String dateCondition) throws Exception {
        SimpleDateFormat formattedDate = new SimpleDateFormat("YYYY-MM-dd");
        Calendar c = Calendar.getInstance();

        /* This logic is implemented taking into consideration the below assumptions and can be refactored given the
           requirements are agreed.
           Assumptions made on date requirements:
           1) Check-in date cannot be in past
           2) Check-out date cannot be prior to Check-in date
           3) Check-in and Check-out dates can be any dates in future
        */

        switch(dateCondition) {
            case "today":
                c.add(Calendar.DATE, 0);
                break;
            case "tomorrow":
                c.add(Calendar.DATE, 1);
                break;
            case "yesterday":
                c.add(Calendar.DATE, -1);
                break;
            case "30 days later":
                c.add(Calendar.DATE, 30);
                break;
            case "":
                break;
            default:
                throw new IllegalArgumentException("Date condition [" + dateCondition + "] is not configured.");
        }

        return dateCondition == "" ? "" : formattedDate.format(c.getTime());
    }
}
