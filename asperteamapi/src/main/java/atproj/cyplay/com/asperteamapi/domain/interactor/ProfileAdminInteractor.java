package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.User;

/**
 * Created by andre on 11-Apr-18.
 */

public interface ProfileAdminInteractor {
    void getUser(String id, final ResourceRequestCallback<User> callback);
}
