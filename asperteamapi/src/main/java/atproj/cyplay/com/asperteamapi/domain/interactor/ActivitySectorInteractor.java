package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.ActivitySector;

import java.util.List;

/**
 * Created by andre on 11-Apr-18.
 */

public interface ActivitySectorInteractor {

    void getAllActivitySectors(final ResourceRequestCallback<List<ActivitySector>> callback);
    void getActivitySector(int id, final ResourceRequestCallback<ActivitySector> callback);
}
