package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.ActivitySector;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-May-18.
 */

public class ActivitySectorInteractorImpl implements ActivitySectorInteractor {

    private AsperTeamApi _asperTeamApi;

    public ActivitySectorInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    @Override
    public void getAllActivitySectors(ResourceRequestCallback<List<ActivitySector>> callback) {
        final ResourceRequestCallback<List<ActivitySector>> fCallback = callback;

        try {
            Call<List<ActivitySector>> call = _asperTeamApi.getAllActivitySectors();
            call.enqueue(new Callback<List<ActivitySector>>() {
                @Override
                public void onResponse(Call<List<ActivitySector>> call, Response<List<ActivitySector>> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<ActivitySector>> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    @Override
    public void getActivitySector(int id, ResourceRequestCallback<ActivitySector> callback) {
        final ResourceRequestCallback<ActivitySector> fCallback = callback;

        try {
            Call<ActivitySector> call = _asperTeamApi.getActivitySector(id);
            call.enqueue(new Callback<ActivitySector>() {
                @Override
                public void onResponse(Call<ActivitySector> call, Response<ActivitySector> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<ActivitySector> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
