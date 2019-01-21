package com.cyplay.atproj.asperteam.dagger.module;

import android.app.Application;

import com.cyplay.atproj.asperteam.facebook.FacebookManager;
import com.cyplay.atproj.asperteam.facebook.IFacebook;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 06-Apr-18.
 */

@Module
public class FacebookModule {

    @Provides
    IFacebook provideFacebook(Application application, Gson gson) {
        FacebookSdk.sdkInitialize(application.getApplicationContext());
        AppEventsLogger.activateApp(application);

        return new FacebookManager(gson);
    }
}
