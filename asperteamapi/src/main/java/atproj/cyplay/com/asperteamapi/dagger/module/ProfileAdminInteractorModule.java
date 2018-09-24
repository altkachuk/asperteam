package atproj.cyplay.com.asperteamapi.dagger.module;

import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractorImpl;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Mar-18.
 */

@Module
public class ProfileAdminInteractorModule {

    boolean offline_mode = false;

    public ProfileAdminInteractorModule() {
        ;
    }

    @Provides
    ProfileAdminInteractor provideProfileAdminInteractor(AsperTeamApi asperTeamApi) {
        return new ProfileAdminInteractorImpl(asperTeamApi);
    }

}
