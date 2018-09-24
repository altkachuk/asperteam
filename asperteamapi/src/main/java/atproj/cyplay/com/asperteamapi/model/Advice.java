package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 04-May-18.
 */

public class Advice {

    int id;
    String text;
    String date_time;
    String dt_added;
    String user;

    public Advice(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return user;
    }

}
