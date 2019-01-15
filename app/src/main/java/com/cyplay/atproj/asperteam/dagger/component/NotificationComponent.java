package com.cyplay.atproj.asperteam.dagger.component;

import com.cyplay.atproj.asperteam.dagger.module.NotificationModule;
import com.cyplay.atproj.asperteam.utils.MailgunSender;
import com.cyplay.atproj.asperteam.utils.NotificationSender;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import dagger.Component;

/**
 * Created by andre on 06-Apr-18.
 */

@Component(modules = {NotificationModule.class}, dependencies = {ApplicationComponent.class})
public interface NotificationComponent {

    NotificationSender getNotificationSender();
}
