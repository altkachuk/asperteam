package com.cyplay.atproj.asperteam.dagger.module;

import android.app.Application;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.utils.NotificationSender;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 06-Apr-18.
 */

@Module
public class NotificationModule {

    @Provides
    NotificationSender provideNotificationSender(Application application) {
        return new NotificationSender(application.getApplicationContext().getString(R.string.server_key));
    }

}
