package atproj.cyplay.com.asperteamapi.domain.repository;

import java.io.IOException;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by andre on 28-Mar-18.
 */

public class TokenAuthenticator implements Authenticator {

    private UserSettingsUtil _userSettings;

    public TokenAuthenticator(UserSettingsUtil userSettings) {
        _userSettings = userSettings;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (_userSettings.getToken() != null) {
            return response.request().newBuilder().
                    header("Authorization", "Token " + _userSettings.getToken()).
                    build();
        }
        return null;
    }
}
