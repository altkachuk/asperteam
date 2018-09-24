package atproj.cyplay.com.asperteamapi.domain.interactor;

import android.os.Handler;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Patient;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

public class ProfileInteractorOfflineImpl implements ProfileInteractor {

    private AsperTeamApi _asperTeamApi;

    public ProfileInteractorOfflineImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void getPatient(String id, ResourceRequestCallback<User> callback) {
        final ResourceRequestCallback<User> getPatientCallback = callback;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPatientCallback.onSucess(getPatientUser());
            }
        }, 300);
    }

    public void getPatients(String id, ResourceRequestCallback<List<User>> callback) {
        final ResourceRequestCallback<List<User>> fCallback  = callback;

        final List<User> patients = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            patients.add(getPatientUser());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fCallback.onSucess(patients);
            }
        }, 300);
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
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoPart);

        try {
            Call<User> userCall = _asperTeamApi.updateUserPhoto(id, usernamePart, photo);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getStaffCallback.onSucess(getStaffUser());
            }
        }, 300);
    }

    private final User getPatientUser() {
        HashMap<String, Integer> staff = new HashMap<>();
        staff.put("https://api.anticipstress.eu/v1/users/COACH", 2);
        staff.put("https://api.anticipstress.eu/v1/users/COACH_PERMANENT", 3);
        staff.put("https://api.anticipstress.eu/v1/users/PROFILE_MANAGER", 4);
        staff.put("https://api.anticipstress.eu/v1/users/HR_MANAGER", 5);
        staff.put("https://api.anticipstress.eu/v1/users/TUTOR", 6);
        staff.put("https://api.anticipstress.eu/v1/users/DISABILITY_MANAGER", 7);

        final User user = new User();
        user.setUsername("patient_for_test");
        user.setFirstName("Andrey");
        user.setLastName("Tkachuk");
        user.setEmail("my@mail.eu");
        user.setImage("https://cdn4.techly.com.au/wp-content/uploads/2018/03/techly-smartphone-camera-noise-799x423.jpg");
        user.setPhone("+380978900850");
        user.setStaff(staff);

        return user;
    }

    private final User getStaffUser() {
        final User user = new User();
        user.setUsername("coach");
        user.setFirstName("Name");
        user.setLastName("Surname");
        user.setEmail("coach@mail.eu");
        user.setImage("https://cdn4.techly.com.au/wp-content/uploads/2018/03/techly-smartphone-camera-noise-799x423.jpg");
        user.setPhone("+380978900850");

        return user;
    }
}
