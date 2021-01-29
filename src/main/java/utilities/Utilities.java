package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Utilities {

    public static String getUniqueId() {
        Date dNow = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyMMddhhmmssMs");
        return formattedDate.format(dNow);
    }

    public static String getDates(String dateCondition) throws Exception {
        SimpleDateFormat formattedDate = new SimpleDateFormat("YYYY-MM-dd");
        Calendar c = Calendar.getInstance();

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
            default:
                throw new IllegalArgumentException("Date condition [" + dateCondition + "] is not configured.");
        }

        return formattedDate.format(c.getTime());
    }
}
