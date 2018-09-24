package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 21-Apr-18.
 */

public class SituationResource {

    Integer id;
    String image;
    String title;
    String description;
    String url;
    String training_content_id;
    Boolean is_published;
    Integer situation;

    public SituationResource(String url, String title, int situation) {
        this.url = url;
        this.title = title;
        this.situation = situation;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPublished() {
        return  is_published;
    }

    public String getTrainingContentId() {
        return training_content_id;
    }

    public int getSituation() {
        return situation;
    }
}
