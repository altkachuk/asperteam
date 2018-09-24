package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.CrossknowledgeModule;
import atproj.cyplay.com.asperteamapi.util.Crossknowledge;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.UserSettingsComponent;
import dagger.Component;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {CrossknowledgeModule.class}, dependencies = {ApplicationComponent.class, UserSettingsComponent.class})
public interface CrossknowledgeComponent {
    Crossknowledge getCrossknowledge();
}
