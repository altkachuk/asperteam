package atproj.cyplay.com.asperteamapi.domain.interactor;

import java.util.List;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;

import atproj.cyplay.com.asperteamapi.model.AvgStress;
import atproj.cyplay.com.asperteamapi.model.Stress;

/**
 * Created by andre on 11-Apr-18.
 */

public interface StressInteractor {

    void addRmssd(String userId, int level, int rri, final ResourceRequestCallback<Stress> callback);
    void addStress(String userId, int level, int rri, double lat, double lng, final ResourceRequestCallback<Stress> callback);

    void getStress(String userId, String startTime, String endTime, final ResourceRequestCallback<List<Stress>> callback);
    void getAvgRmmssd(String userId, String startTime, String endTime, final ResourceRequestCallback<List<AvgStress>> callback);
}
