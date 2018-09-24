package atproj.cyplay.com.asperteamapi.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by andre on 12-Jun-18.
 */

public class CalendarUtil {

    private static final String date_format_pattern = "yyyy-MM-dd";
    private static final String date_time_format_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String time_format_pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";

    private static final long mls_in_sec = 1000l;
    private static final long sec_in_min = 60l;
    private static final long min_in_hr = 60l;
    private static final long hr_in_day = 24l;

    public static String getTodayDate() {
        Calendar today = Calendar.getInstance();
        DecimalFormat dblDecFormat = new DecimalFormat("00");
        return today.get(Calendar.YEAR) + "-" +
                dblDecFormat.format(today.get(Calendar.MONTH)+1) + "-" +
                dblDecFormat.format(today.get(Calendar.DAY_OF_MONTH));

    }

    public static String getDay(int month, int week, int day) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, month);
        today.add(Calendar.WEEK_OF_MONTH, week);
        today.add(Calendar.DAY_OF_MONTH, day);
        DecimalFormat dblDecFormat = new DecimalFormat("00");

        return today.get(Calendar.YEAR) + "-" +
                dblDecFormat.format(today.get(Calendar.MONTH)+1) + "-" +
                dblDecFormat.format(today.get(Calendar.DAY_OF_MONTH));
    }

    public static int birthDateToAge(String birthDate) {
        if (birthDate == null)
            return 0;

        SimpleDateFormat format = new SimpleDateFormat(date_time_format_pattern);
        Calendar todayDate = Calendar.getInstance();
        Calendar bDate = Calendar.getInstance();
        try {
            bDate.setTime(format.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int age = todayDate.get(Calendar.YEAR) - bDate.get(Calendar.YEAR);

        if (todayDate.get(Calendar.YEAR) < bDate.get(Calendar.YEAR))
            age--;

        return age;
    }

    public static int dateToDayUnix(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat(date_format_pattern);
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(format.parse(strDate));
        } catch (ParseException e) {e.printStackTrace();};

        int result  = (int)(date.getTimeInMillis() / mls_in_sec / sec_in_min / min_in_hr / hr_in_day);
        return result;
    }

    public static String dayUnixToDate(int dayAsperteamUnix) {
        Calendar date = Calendar.getInstance();

        date.setTimeInMillis((long)dayAsperteamUnix*mls_in_sec*sec_in_min*min_in_hr*hr_in_day);

        DecimalFormat dblDecFormat = new DecimalFormat("00");

        String result = date.get(Calendar.YEAR) + "-" +
                dblDecFormat.format(date.get(Calendar.MONTH)+1) + "-" +
                dblDecFormat.format(date.get(Calendar.DAY_OF_MONTH));
        return result;
    }
}
