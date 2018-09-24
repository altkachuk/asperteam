package com.cyplay.atproj.asperteam.dagger.module;

import android.content.Context;

import com.cyplay.atproj.asperteam.utils.MsBandManager;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Apr-18.
 */

@Module
public class BandModule {

    @Provides
    MsBandManager provideMsBandManager(Context context, UserSettingsUtil userSettings) {
        MsBandManager msBandManager = MsBandManager.getInstance(context);
        msBandManager.setLevelMax(userSettings.getStressLevelMax());
        msBandManager.setBandUseAgree(userSettings.isBandUseAgree());
        return msBandManager;
    }
}
