package atproj.cyplay.com.asperteamapi.domain.repository;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by andre on 28-Mar-18.
 */

public class AdminTokenAuthenticator implements Authenticator {

    private static String admin_token = "f42946f30ac7ec4f226707627c9b49e1dafc2c0e";

    public AdminTokenAuthenticator() {
        ;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return response.request().newBuilder().
                    header("Authorization", "Token " + admin_token).
                    build();
    }
}
