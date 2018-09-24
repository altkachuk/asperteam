package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 22-Mar-18.
 */

public class AvgStress {

    Double avg_stress;
    Integer hour;
    String date;

    public AvgStress(Double avg_stress, Integer hour) {
        this.avg_stress = avg_stress;
        this.hour = hour;
    }

    public AvgStress(Double avg_stress, String date) {
        this.avg_stress = avg_stress;
        this.date = date;
    }

    public Float getAvgStress() {
        return avg_stress.floatValue();
    }

    public Integer getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }
}
