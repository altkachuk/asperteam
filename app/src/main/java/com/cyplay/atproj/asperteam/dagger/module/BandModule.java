package com.cyplay.atproj.asperteam.dagger.module;

import android.content.Context;

import com.cyplay.atproj.asperteam.band.BandManager;
import com.cyplay.atproj.asperteam.band.IBand;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Apr-18.
 */

@Module
public class BandModule {

    private final BandManager _bandManager;

    public BandModule(Context context) {
        _bandManager = new BandManager(context);
    }

    @Provides
    IBand provideBandManager(UserSettingsUtil userSettings, StressInteractor stressInteractor) {
        _bandManager.init(stressInteractor, userSettings.getId(), userSettings.getStressLevelMax());
        return _bandManager;
    }
}
