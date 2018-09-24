package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.OkHttpClientModule;
import com.google.gson.Gson;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import dagger.Component;
import okhttp3.OkHttpClient;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {OkHttpClientModule.class}, dependencies = {ApplicationComponent.class, UserSettingsComponent.class})
public interface OkHttpClientComponent {
    Gson getGson();
    OkHttpClient getOkHttpClient();
}
