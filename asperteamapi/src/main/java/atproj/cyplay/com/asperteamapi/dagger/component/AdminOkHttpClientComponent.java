package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.AdminOkHttpClientModule;
import com.google.gson.Gson;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import dagger.Component;
import okhttp3.OkHttpClient;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {AdminOkHttpClientModule.class}, dependencies = {ApplicationComponent.class})
public interface AdminOkHttpClientComponent {
    Gson getGson();
    OkHttpClient getOkHttpClient();
}
