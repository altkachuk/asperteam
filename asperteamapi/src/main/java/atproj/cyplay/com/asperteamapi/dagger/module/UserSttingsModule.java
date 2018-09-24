package atproj.cyplay.com.asperteamapi.dagger.module;

import android.content.Context;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 30-Mar-18.
 */

@Module
public class UserSttingsModule {

    @Provides
    UserSettingsUtil provideUserSettings(Context context) {
        UserSettingsUtil userSettingsUtil = new UserSettingsUtil(context);
        return userSettingsUtil;
    }
}
