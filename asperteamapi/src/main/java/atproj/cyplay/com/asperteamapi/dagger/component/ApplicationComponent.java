package atproj.cyplay.com.asperteamapi.dagger.component;

import android.app.Application;
import android.content.Context;

import atproj.cyplay.com.asperteamapi.dagger.module.ApplicationModule;

import dagger.Component;

/**
 * Created by andre on 30-Mar-18.
 */

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Application getApplication();
    Context getContext();
}
