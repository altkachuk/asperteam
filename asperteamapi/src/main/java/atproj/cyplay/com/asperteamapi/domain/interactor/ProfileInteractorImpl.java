package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Patient;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class ProfileInteractorImpl implements ProfileInteractor {

    private AsperTeamApi _asperTeamApi;

    public ProfileInteractorImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void getPatient(String id, ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> getPatientCallback = callback;

        try {
            Call<User> userCall = _asperTeamApi.getUser(id);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (getPatientCallback != null) {
                        if (response.body() != null) {
                            getPatientCallback.onSucess(response.body());
                        } else
                            getPatientCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    getPatientCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getPatientCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void getPatients(String id, ResourceRequestCallback<List<User>> callback) {
        ;
    }

    public void getPatientProfile(String id, ResourceRequestCallback<Patient> callback) {
        final ResourceRequestCallback<Patient> getPatientProfileCallback = callback;

        try {
            Call<Patient> profileCall = _asperTeamApi.getProfile(id);
            profileCall.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (getPatientProfileCallback != null) {
                        if (response.body() != null)
                            getPatientProfileCallback.onSucess(response.body());
                        else
                            getPatientProfileCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    getPatientProfileCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getPatientProfileCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void updateUser(String id, String username, User user, final ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> updateUserCallback = callback;
        user.setUsername(username);

        try {
            Call<User> userCall = _asperTeamApi.updateUser(id, user);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (updateUserCallback != null) {
                        if (response.body() != null)
                            updateUserCallback.onSucess(response.body());
                        else
                            updateUserCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    updateUserCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });
        } catch (Exception e) {
            updateUserCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void updatePhotoProfile(String id, String username, final File file, final ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> updatePatientPhotoCallback = callback;

        RequestBody usernamePart = RequestBody.create(MediaType.parse("text/plain"), username);

        RequestBody photoPart = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), photoPart);

        try {
            Call<User> userCall = _asperTeamApi.updateUserPhoto(id, usernamePart, image);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (updatePatientPhotoCallback != null) {
                        if (response.body() != null)
                            updatePatientPhotoCallback.onSucess(response.body());
                        else
                            updatePatientPhotoCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                    file.delete();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    updatePatientPhotoCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                    file.delete();
                }
            });
        } catch (Exception e) {
            updatePatientPhotoCallback.onError(new BaseException(ExceptionType.TECHNICAL));
            file.delete();
        }
    }

    public void getStaff(String id, ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> getStaffCallback = callback;

        try {
            Call<User> userCall = _asperTeamApi.getUser(id);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (getStaffCallback != null) {
                        if (response.body() != null)
                            getStaffCallback.onSucess(response.body());
                        else
                            getStaffCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    getStaffCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            getStaffCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }
}
