package atproj.cyplay.com.asperteamapi.model;

import java.util.List;

/**
 * Created by andre on 28-Mar-18.
 */

public class OAuth2Credentials {

    String token;
    String id;

    public OAuth2Credentials(String token, String id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}
