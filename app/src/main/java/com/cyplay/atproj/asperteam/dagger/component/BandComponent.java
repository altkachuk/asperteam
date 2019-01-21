package com.cyplay.atproj.asperteam.dagger.component;

import com.cyplay.atproj.asperteam.band.IBand;
import com.cyplay.atproj.asperteam.dagger.module.BandModule;
import com.cyplay.atproj.asperteam.band.BandManager;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.InteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.UserSettingsComponent;
import dagger.Component;

/**
 * Created by andre on 24-Apr-18.
 */

@Component(modules = {BandModule.class}, dependencies = {ApplicationComponent.class, UserSettingsComponent.class, InteractorComponent.class})
public interface BandComponent {
    IBand bandManager();
}
