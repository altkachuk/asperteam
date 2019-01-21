package com.cyplay.atproj.asperteam.dagger.component;

import com.cyplay.atproj.asperteam.dagger.module.FacebookModule;
import com.cyplay.atproj.asperteam.facebook.FacebookManager;
import com.cyplay.atproj.asperteam.facebook.IFacebook;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.OkHttpClientComponent;
import dagger.Component;

/**
 * Created by andre on 06-Apr-18.
 */

@Component(modules = {FacebookModule.class}, dependencies = {ApplicationComponent.class, OkHttpClientComponent.class})
public interface FacebookComponent {

    IFacebook getFacebook();
}
