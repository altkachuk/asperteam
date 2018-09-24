package atproj.cyplay.com.asperteamapi.dagger.module;

import atproj.cyplay.com.asperteamapi.domain.interactor.ActivitySectorInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ActivitySectorInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.JobInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.JobInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.LanguageInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LanguageInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.LoginInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LoginInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.LoginInteractorOfflineImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractorOfflineImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractorOfflineImpl;
import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Mar-18.
 */

@Module
public class InteractorModule {

    boolean offline_mode = false;

    public InteractorModule() {
        ;
    }

    @Provides
    LoginInteractor provideLoginInteractor(AsperTeamApi asperTeamApi) {
        if (offline_mode)
            return new LoginInteractorOfflineImpl(asperTeamApi);
        return new LoginInteractorImpl(asperTeamApi);
    }

    @Provides
    ProfileInteractor provideProfileInteractor(AsperTeamApi asperTeamApi) {
        if (offline_mode)
            return  new ProfileInteractorOfflineImpl(asperTeamApi);
        return new ProfileInteractorImpl(asperTeamApi);
    }

    @Provides
    SituationInteractor provideSituationInteractor(AsperTeamApi asperTeamApi) {
        if (offline_mode)
            return  new SituationInteractorOfflineImpl(asperTeamApi);
        return new SituationInteractorImpl(asperTeamApi);
    }

    @Provides
    StressInteractor provideStressInteractor(AsperTeamApi asperTeamApi) {
        return new StressInteractorImpl(asperTeamApi);
    }

    @Provides
    JobInteractor provideJobInteractor(AsperTeamApi asperTeamApi) {
        return new JobInteractorImpl(asperTeamApi);
    }

    @Provides
    LanguageInteractor provideLanguageInteractor(AsperTeamApi asperTeamApi) {
        return new LanguageInteractorImpl(asperTeamApi);
    }

    @Provides
    ActivitySectorInteractor provideActivitySectorInteractor(AsperTeamApi asperTeamApi) {
        return new ActivitySectorInteractorImpl(asperTeamApi);
    }

}
