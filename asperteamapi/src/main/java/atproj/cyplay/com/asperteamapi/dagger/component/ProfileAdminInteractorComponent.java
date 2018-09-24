package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.ProfileAdminInteractorModule;

import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractor;

import dagger.Component;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {ProfileAdminInteractorModule.class}, dependencies = {AdminNetComponent.class})
public interface ProfileAdminInteractorComponent {
    ProfileAdminInteractor getProfileAdminInteractor();
}
