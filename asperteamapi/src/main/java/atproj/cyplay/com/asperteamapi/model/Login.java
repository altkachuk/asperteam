package atproj.cyplay.com.asperteamapi.model;

/**
 * Created by andre on 28-Mar-18.
 */

public class Login {

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login(String username, String password, String firebaseToken) {
        this.username = username;
        this.password = password;
        this.firebase_token = firebaseToken;
    }

    String username;
    String password;
    String firebase_token;
}
