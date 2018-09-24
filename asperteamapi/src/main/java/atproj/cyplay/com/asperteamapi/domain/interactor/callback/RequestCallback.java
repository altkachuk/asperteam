package atproj.cyplay.com.asperteamapi.domain.interactor.callback;

import atproj.cyplay.com.asperteamapi.model.exception.BaseException;

/**
 * Created by andre on 11-Apr-18.
 */

public interface RequestCallback {

    void onError(BaseException e);
}
