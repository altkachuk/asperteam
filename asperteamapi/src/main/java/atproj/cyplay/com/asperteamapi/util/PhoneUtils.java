package atproj.cyplay.com.asperteamapi.util;

/**
 * Created by andre on 22-May-18.
 */

public class PhoneUtils {

    static private String non_number_regex = "\\D";

    static public String validate(String sourceNumber) {
        String number = sourceNumber.replaceAll(non_number_regex, "");
        while (number.length() < 11) {
            number = "3" + number;
        }
        number = "+" + number;
        return number;
    }
}
