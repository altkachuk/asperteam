package atproj.cyplay.com.asperteamapi.domain.repository;

import atproj.cyplay.com.asperteamapi.model.ActivitySector;
import atproj.cyplay.com.asperteamapi.model.Advice;
import atproj.cyplay.com.asperteamapi.model.AvgStress;
import atproj.cyplay.com.asperteamapi.model.Job;
import atproj.cyplay.com.asperteamapi.model.Language;
import atproj.cyplay.com.asperteamapi.model.Login;
import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;
import atproj.cyplay.com.asperteamapi.model.Patient;
import atproj.cyplay.com.asperteamapi.model.Situation;
import atproj.cyplay.com.asperteamapi.model.SituationResource;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.Stress;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andre on 22-Mar-18.
 */

public interface AsperTeamApi {

    static final String VERSION_1 = "v1";

    @POST("authenticate/")
    Call<OAuth2Credentials> authenticate(
            @Body Login login);

    @GET(VERSION_1 + "/users/{id}/")
    Call<User> getUser(
            @Path("id") String id);

    @GET(VERSION_1 + "/users/{id}/")
    Call<Patient> getProfile(
            @Path("id") String id);

    @PUT(VERSION_1 + "/users/{id}/")
    Call<User> updateUser(
            @Path("id") String id,
            @Body User user);

    @Multipart
    @PUT(VERSION_1 + "/users/{id}/")
    Call<User> updateUserPhoto(
            @Path("id") String id,
            @Part("username") RequestBody username,
            @Part MultipartBody.Part image);

    @GET(VERSION_1 + "/patients/{id}/staff")
    Call<List<User>> getStaffs(
            @Path("id") String patientId);

    @POST(VERSION_1 + "/stress/")
    Call<Stress> addStress(
            @Body Stress stress);

    @GET(VERSION_1 + "/stress")
    Call<List<Stress>> getStress(
            @Query("user") String userId,
            @Query("date_time__gte") String startDateTime,
            @Query("date_time__lte") String endDateTime,
            @Query("lat__gt") Double gtLat,
            @Query("lng__gt") Double gtLng);

    @GET(VERSION_1 + "/avg-stress")
    Call<List<AvgStress>> getAvgStress(
            @Query("user") String userId,
            @Query("date_time__gte") String startDateTime,
            @Query("date_time__lte") String endDateTime);

    @GET(VERSION_1 + "/coaches/{id}")
    Call<User> getCoach(
            @Path("id") String coachId);

    @PUT(VERSION_1 + "/coaches/{id}")
    Call<User> updateCoach(
            @Path("id") String coachId,
            @Body User patient);

    @GET(VERSION_1 + "/staff/{id}")
    Call<User> getStaff(
            @Path("id") String staffId);

    @PUT(VERSION_1 + "/staff/{id}")
    Call<User> updateStaff(
            @Path("id") String staffId,
            @Body User patient);

    @GET(VERSION_1 + "/languages/")
    Call<List<Language>> getAllLanguages();


    @GET(VERSION_1 + "/activity-sectors/")
    Call<List<ActivitySector>> getAllActivitySectors();

    @GET(VERSION_1 + "/activity-sectors/{id}")
    Call<ActivitySector> getActivitySector(
            @Path("id") int activitySectorId);

    @GET(VERSION_1 + "/situations/")
    Call<List<Situation>> getSituations(
            @Query("type") String type);

    @GET(VERSION_1 + "/resources/")
    Call<List<SituationResource>> getSituationResources(@Query("situation") int situationId);

    @GET(VERSION_1 + "/advices/")
    Call<List<Advice>> getAdvices(
            @Query("date_time") String dateTime);

    @GET(VERSION_1 + "/jobs/{id}")
    Call<Job> getJob(
            @Path("id") int jobId);

    @PUT(VERSION_1 + "/jobs/{id}")
    Call<Job> updateJob(
            @Path("id") Integer jobId,
            @Body Job job);

    @POST(VERSION_1 + "/jobs/")
    Call<Job> createJob(
            @Body Job job);
}
