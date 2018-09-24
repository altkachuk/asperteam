package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;

import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class ProfileAdminInteractorImpl implements ProfileAdminInteractor {

    private AsperTeamApi _asperTeamApi;

    public ProfileAdminInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void getUser(String id, ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> fCallback = callback;

        try {
            Call<User> userCall = _asperTeamApi.getUser(id);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
