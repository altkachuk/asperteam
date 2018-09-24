package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 22-Mar-18.
 */

public class Stress {

    String id;
    Integer level;
    String date_time;
    Double lat;
    Double lng;
    String user;

    public Stress(String user, Integer level, Double lat, Double lng) {
        this.user = user;
        this.level = level;
        this.lat = lat;
        this.lng = lng;
    }

    public Stress(String user, Integer level, String dateTime) {
        this.user = user;
        this.level = level;
        this.date_time = dateTime;
    }

    public Stress(String user, Integer level) {
        this.user = user;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public String getDateTime() {
        return date_time;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getUser() {
        return user;
    }
}
