package atproj.cyplay.com.asperteamapi.domain.interactor;

import android.os.Handler;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.AvgStress;
import atproj.cyplay.com.asperteamapi.model.Stress;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class StressInteractorImpl implements StressInteractor {

    AsperTeamApi _asperTeamApi;

    public StressInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void addRmssd(String userId, int level, int rri, final ResourceRequestCallback<Stress> callback) {
        final ResourceRequestCallback<Stress> addStressCallback = callback;

        Stress stress = new Stress(userId, level, rri);

        try {
            Call<Stress> addStressCall = _asperTeamApi.addStress(stress);
            addStressCall.enqueue(new Callback<Stress>() {
                @Override
                public void onResponse(Call<Stress> call, Response<Stress> response) {
                    if (addStressCallback != null) {
                        if (response.body() != null)
                            addStressCallback.onSucess(response.body());
                        else
                            addStressCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Stress> call, Throwable t) {
                    addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void addStress(String userId, int level, int rri, double lat, double lng, final ResourceRequestCallback<Stress> callback) {
        final ResourceRequestCallback<Stress> addStressCallback = callback;

        Stress stress = new Stress(userId, level, rri, lat, lng);

        try {
            Call<Stress> addStressCall = _asperTeamApi.addStress(stress);
            addStressCall.enqueue(new Callback<Stress>() {
                @Override
                public void onResponse(Call<Stress> call, Response<Stress> response) {
                    if (addStressCallback != null) {
                        if (response.body() != null)
                            addStressCallback.onSucess(response.body());
                        else
                            addStressCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Stress> call, Throwable t) {
                    addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }


    public void getStress(String userId, String startTime, String endTime, final ResourceRequestCallback<List<Stress>> callback) {
        final ResourceRequestCallback<List<Stress>> fCallback = callback;

        try {
            Call<List<Stress>> addStressCall = _asperTeamApi.getStress(userId, startTime, endTime, 0.0, 0.0);
            addStressCall.enqueue(new Callback<List<Stress>>() {
                @Override
                public void onResponse(Call<List<Stress>> call, Response<List<Stress>> response) {
                    if (fCallback != null) {
                        if (response.body() != null)
                            fCallback.onSucess(response.body());
                        else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<Stress>> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void getAvgRmmssd(String userId, String startTime, String endTime, final ResourceRequestCallback<List<AvgStress>> callback) {
        final ResourceRequestCallback<List<AvgStress>> fCallback = callback;

        try {
            Call<List<AvgStress>> call = _asperTeamApi.getAvgStress(userId, startTime, endTime);
            call.enqueue(new Callback<List<AvgStress>>() {
                @Override
                public void onResponse(Call<List<AvgStress>> call, Response<List<AvgStress>> response) {
                    if (fCallback != null) {
                        if (response.body() != null)
                            fCallback.onSucess(response.body());
                        else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<AvgStress>> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
