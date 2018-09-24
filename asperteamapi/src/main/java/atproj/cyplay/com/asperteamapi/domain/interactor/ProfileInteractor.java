package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Patient;
import atproj.cyplay.com.asperteamapi.model.User;

import java.io.File;
import java.util.List;

/**
 * Created by andre on 11-Apr-18.
 */

public interface ProfileInteractor {

    void getPatient(String id, final ResourceRequestCallback<User> callback);
    void getPatients(String id, ResourceRequestCallback<List<User>> callback);
    void getPatientProfile(String id, final ResourceRequestCallback<Patient> callback);

    void updateUser(String id, String username, User user, final ResourceRequestCallback<User> callback);

    void updatePhotoProfile(String id, String username, final File file, final ResourceRequestCallback<User> callback);

    void getStaff(String id, final ResourceRequestCallback<User> callback);
}
