package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 28-Mar-18.
 */

public class Job {

    Integer id;
    String title;
    String company;
    Double duration;
    String user;


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public Double getDuration() {
        return duration;
    }

    public String getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public void setCompany(String value) {
        this.company = value;
    }

    public void setDuration(Double value) {
        this.duration = value;
    }



}
