package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.UserSttingsModule;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Component;

/**
 * Created by andre on 30-Mar-18.
 */

@Component(modules = {UserSttingsModule.class}, dependencies = {ApplicationComponent.class})
public interface UserSettingsComponent {
    UserSettingsUtil getUserSettings();
}
