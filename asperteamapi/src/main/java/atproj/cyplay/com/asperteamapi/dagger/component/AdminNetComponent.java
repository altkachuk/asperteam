package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.AdminNetModule;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;

import dagger.Component;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {AdminNetModule.class}, dependencies = {AdminOkHttpClientComponent.class})
public interface AdminNetComponent {
    AsperTeamApi getAsperTeamApiService();
}
