package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Login;
import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;

/**
 * Created by andre on 11-Apr-18.
 */

public interface LoginInteractor {
    void authenticate(Login login, final ResourceRequestCallback<OAuth2Credentials> callback);
}
