package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.component.NetComponent;
import atproj.cyplay.com.asperteamapi.dagger.module.InteractorModule;
import atproj.cyplay.com.asperteamapi.domain.interactor.ActivitySectorInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.JobInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LanguageInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LoginInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;

import dagger.Component;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {InteractorModule.class}, dependencies = {NetComponent.class})
public interface InteractorComponent {
    LoginInteractor getLoginInteractor();
    ProfileInteractor getProfileInteractor();
    SituationInteractor getSituationInteractor();
    StressInteractor getStressInteractor();
    JobInteractor getJobInteractor();
    LanguageInteractor getLanguageInteractor();
    ActivitySectorInteractor getActivitySectorInteractor();
}
