package atproj.cyplay.com.asperteamapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andre on 08-Apr-18.
 */

public class FacebookProfile {

    String id;
    String first_name;
    String last_name;
    String link;
    String email;
    Picture picture;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getLink() {
        return link;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl() {
        return picture.getUrl();
    }



    class Picture {
        Data data;

        public String getUrl() {
            return data.getUrl();
        }

        class Data {
            String url;

            public String getUrl() {
                return url;
            }
        }
    }
}
