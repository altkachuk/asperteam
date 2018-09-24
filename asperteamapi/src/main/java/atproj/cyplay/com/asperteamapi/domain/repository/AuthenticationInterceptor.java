package atproj.cyplay.com.asperteamapi.domain.repository;

import java.io.IOException;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andre on 17-Apr-18.
 */

public class AuthenticationInterceptor implements Interceptor {

    private final UserSettingsUtil _userSettings;

    public AuthenticationInterceptor(UserSettingsUtil userSettings) {
        _userSettings = userSettings;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (_userSettings.getToken() == null) {
            return chain.proceed(originalRequest);
        }

        Request authorisedRequest = originalRequest.newBuilder()
                .header("Authorization", "Token " + _userSettings.getToken())
                .build();
        return chain.proceed(authorisedRequest);
    }
}
