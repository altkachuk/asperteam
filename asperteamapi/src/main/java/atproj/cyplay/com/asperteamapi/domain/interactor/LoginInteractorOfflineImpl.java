package atproj.cyplay.com.asperteamapi.domain.interactor;

import android.os.Handler;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Login;
import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;

/**
 * Created by andre on 11-Apr-18.
 */

public class LoginInteractorOfflineImpl implements LoginInteractor {

    AsperTeamApi _asperTeamApi;

    public LoginInteractorOfflineImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void authenticate(Login login, ResourceRequestCallback<OAuth2Credentials> callback) {
        final ResourceRequestCallback<OAuth2Credentials> authenticateCallback = callback;

        final OAuth2Credentials oAuth2Credentials = new OAuth2Credentials("437efc14af8fe1ae3a36e60c9e83c1614bbb8641", "561c9e74-fef3-4d76-b168-917a9e42f012");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                authenticateCallback.onSucess(oAuth2Credentials);
            }
        }, 300);

    }
}
