package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;

import atproj.cyplay.com.asperteamapi.model.Language;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-May-18.
 */

public class LanguageInteractorImpl implements LanguageInteractor {

    private AsperTeamApi _asperTeamApi;

    public LanguageInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    @Override
    public void getAllLanguages(ResourceRequestCallback<List<Language>> callback) {
        final ResourceRequestCallback<List<Language>> fCallback = callback;

        try {
            Call<List<Language>> call = _asperTeamApi.getAllLanguages();
            call.enqueue(new Callback<List<Language>>() {
                @Override
                public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<Language>> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
