package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Advice;
import atproj.cyplay.com.asperteamapi.model.Situation;
import atproj.cyplay.com.asperteamapi.model.SituationResource;

import java.util.List;

/**
 * Created by andre on 21-Apr-18.
 */

public interface SituationInteractor {
    void getSituations(String type, final ResourceRequestCallback<List<Situation>> callback);
    void getSituationResources(int situationId, final ResourceRequestCallback<List<SituationResource>> callback);
    void getAdvices(String dateTime, final ResourceRequestCallback<List<Advice>> callback);

}
