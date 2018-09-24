package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Login;
import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class LoginInteractorImpl implements LoginInteractor {

    AsperTeamApi _asperTeamApi;

    public LoginInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void authenticate(Login login, ResourceRequestCallback<OAuth2Credentials> callback) {
        final ResourceRequestCallback<OAuth2Credentials> authenticateCallback = callback;

        try {
            Call<OAuth2Credentials> oAuth2CredentialsCall = _asperTeamApi.authenticate(login);
            oAuth2CredentialsCall.enqueue(new Callback<OAuth2Credentials>() {
                @Override
                public void onResponse(Call<OAuth2Credentials> call, Response<OAuth2Credentials> response) {
                    if (authenticateCallback != null) {
                        if (response.body() != null) {
                            authenticateCallback.onSucess(response.body());
                        } else
                            authenticateCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<OAuth2Credentials> call, Throwable t) {
                    authenticateCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });
        } catch (Exception e) {
            authenticateCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }

    }
}
