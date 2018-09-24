package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Advice;
import atproj.cyplay.com.asperteamapi.model.Situation;
import atproj.cyplay.com.asperteamapi.model.SituationResource;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class SituationInteractorImpl implements SituationInteractor {

    AsperTeamApi _asperTeamApi;

    public SituationInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void getSituations(String type, final ResourceRequestCallback<List<Situation>> callback) {
        final ResourceRequestCallback<List<Situation>> getSituationsCallback = callback;

        try {
            Call<List<Situation>> situationsCall = _asperTeamApi.getSituations(type);
            situationsCall.enqueue(new Callback<List<Situation>>() {
                @Override
                public void onResponse(Call<List<Situation>> call, Response<List<Situation>> response) {
                    if (getSituationsCallback != null) {
                        if (response.body() != null)
                            getSituationsCallback.onSucess(response.body());
                        else
                            getSituationsCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<Situation>> call, Throwable t) {
                    getSituationsCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getSituationsCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void getSituationResources(int situationId, final ResourceRequestCallback<List<SituationResource>> callback) {
        final ResourceRequestCallback<List<SituationResource>> getSituationResourcesCallback = callback;

        try {
            Call<List<SituationResource>> situationResourcesCall = _asperTeamApi.getSituationResources(situationId);
            situationResourcesCall.enqueue(new Callback<List<SituationResource>>() {
                @Override
                public void onResponse(Call<List<SituationResource>> call, Response<List<SituationResource>> response) {
                    if (getSituationResourcesCallback != null) {
                        if (response.body() != null)
                            getSituationResourcesCallback.onSucess(response.body());
                        else
                            getSituationResourcesCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<SituationResource>> call, Throwable t) {
                    getSituationResourcesCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getSituationResourcesCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }

    }

    public void getAdvices(String dateTime, final ResourceRequestCallback<List<Advice>> callback) {
        final ResourceRequestCallback<List<Advice>> getAdviceCallback = callback;

        try {
            Call<List<Advice>> adviceCall = _asperTeamApi.getAdvices(dateTime);
            adviceCall.enqueue(new Callback<List<Advice>>() {
                @Override
                public void onResponse(Call<List<Advice>> call, Response<List<Advice>> response) {
                    if (getAdviceCallback != null) {
                        if (response.body() != null)
                            getAdviceCallback.onSucess(response.body());
                        else
                            getAdviceCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<Advice>> call, Throwable t) {
                    getAdviceCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getAdviceCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
