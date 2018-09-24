package atproj.cyplay.com.asperteamapi.dagger.module;

import android.app.Application;

import atproj.cyplay.com.asperteamapi.R;

import atproj.cyplay.com.asperteamapi.util.Crossknowledge;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Mar-18.
 */

@Module
public class CrossknowledgeModule {

    public CrossknowledgeModule() {
        ;
    }

    @Provides
    Crossknowledge provideCrossknowledge(Application application, UserSettingsUtil userSettings) {
        Crossknowledge crossknowledge = new Crossknowledge(
                application.getApplicationContext(),
                R.string.crossknowledge_api,
                R.string.crossknowledge_url,
                R.string.crossknowledge_content,
                userSettings.getUsername());
        return crossknowledge;
    }

}
