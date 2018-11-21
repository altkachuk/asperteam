package com.cyplay.atproj.asperteam.dagger.module;

import android.content.Context;

import com.cyplay.atproj.asperteam.utils.BandManager;
import com.cyplay.atproj.asperteam.utils.MsBandManager;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Apr-18.
 */

@Module
public class BandModule {

    //private final MsBandManager _msBandManager;
    private final BandManager _bandManager;

    public BandModule(Context context) {
        //_msBandManager = new MsBandManager(context);
        _bandManager = new BandManager(context);
    }

    /*@Provides
    MsBandManager provideMsBandManager(UserSettingsUtil userSettings, StressInteractor stressInteractor) {
        _msBandManager.setStressInteractor(stressInteractor);
        _msBandManager.setId(userSettings.getId());
        _msBandManager.setLevelMax(userSettings.getStressLevelMax());
        _msBandManager.setBandUseAgree(userSettings.isBandUseAgree());
        return _msBandManager;
    }*/

    @Provides
    BandManager provideBandManager(UserSettingsUtil userSettings, StressInteractor stressInteractor) {
        _bandManager.setStressInteractor(stressInteractor);
        _bandManager.setId(userSettings.getId());
        _bandManager.setLevelMax(userSettings.getStressLevelMax());
        return _bandManager;
    }
}
