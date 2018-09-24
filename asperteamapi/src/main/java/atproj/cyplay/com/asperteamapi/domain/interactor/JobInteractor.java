package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Job;

/**
 * Created by andre on 11-Apr-18.
 */

public interface JobInteractor {

    void getJob(int id, final ResourceRequestCallback<Job> callback);
    void updateJob(int jobId, Job job, final ResourceRequestCallback<Job> callback);
    void createJob(Job job, final ResourceRequestCallback<Job> callback);
}
