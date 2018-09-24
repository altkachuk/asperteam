package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 21-Apr-18.
 */

public class Situation {

    static public final String STRESS_TYPE = "stress";
    static public final String RELAX_TYPE  = "relax";

    int id;
    String title;
    String description;
    String is_published;

    public Situation(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
