package atproj.cyplay.com.asperteamapi.domain.interactor.callback;

/**
 * Created by andre on 11-Apr-18.
 */

public interface ResourceRequestCallback<Resource> extends RequestCallback {
    void onSucess(Resource resource);
}
