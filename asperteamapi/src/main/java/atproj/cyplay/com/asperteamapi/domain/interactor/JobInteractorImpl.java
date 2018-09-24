package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Job;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-May-18.
 */

public class JobInteractorImpl implements JobInteractor {

    private AsperTeamApi _asperTeamApi;

    public JobInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    @Override
    public void getJob(int id, ResourceRequestCallback<Job> callback) {
        final ResourceRequestCallback<Job> fCallback = callback;

        try {
            Call<Job> call = _asperTeamApi.getJob(id);
            call.enqueue(new Callback<Job>() {
                @Override
                public void onResponse(Call<Job> call, Response<Job> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Job> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    @Override
    public void updateJob(int jobId, Job job, final ResourceRequestCallback<Job> callback) {
        final ResourceRequestCallback<Job> fCallback = callback;

        try {
            Call<Job> call = _asperTeamApi.updateJob(jobId, job);
            call.enqueue(new Callback<Job>() {
                @Override
                public void onResponse(Call<Job> call, Response<Job> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Job> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    @Override
    public void createJob(Job job, final ResourceRequestCallback<Job> callback) {
        final ResourceRequestCallback<Job> fCallback = callback;

        try {
            Call<Job> call = _asperTeamApi.createJob(job);
            call.enqueue(new Callback<Job>() {
                @Override
                public void onResponse(Call<Job> call, Response<Job> response) {
                    if (fCallback != null) {
                        if (response.body() != null) {
                            fCallback.onSucess(response.body());
                        } else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Job> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
