package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.component.OkHttpClientComponent;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;;
import atproj.cyplay.com.asperteamapi.dagger.module.NetModule;

import dagger.Component;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {NetModule.class}, dependencies = {OkHttpClientComponent.class})
public interface NetComponent {
    AsperTeamApi getAsperTeamApiService();
}
