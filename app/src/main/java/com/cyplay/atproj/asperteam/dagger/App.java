package com.cyplay.atproj.asperteam.dagger;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.cyplay.atproj.asperteam.BuildConfig;
import atproj.cyplay.com.asperteamapi.dagger.component.AdminNetComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.ProfileAdminInteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerProfileAdminInteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerAdminNetComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.AdminOkHttpClientComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerAdminOkHttpClientComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import com.cyplay.atproj.asperteam.dagger.component.BandComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.CrossknowledgeComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerCrossknowledgeComponent;
import com.cyplay.atproj.asperteam.dagger.component.DaggerBandComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerApplicationComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerOkHttpClientComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerNetComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerPicassoComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerUserSettingsComponent;
import com.cyplay.atproj.asperteam.dagger.component.FacebookComponent;
import com.cyplay.atproj.asperteam.dagger.component.DaggerFacebookComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.InteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.DaggerInteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.NetComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.OkHttpClientComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.PicassoComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.UserSettingsComponent;
import atproj.cyplay.com.asperteamapi.dagger.module.AdminNetModule;
import atproj.cyplay.com.asperteamapi.dagger.module.AdminOkHttpClientModule;
import atproj.cyplay.com.asperteamapi.dagger.module.ProfileAdminInteractorModule;
import atproj.cyplay.com.asperteamapi.dagger.module.ApplicationModule;

import com.cyplay.atproj.asperteam.dagger.component.NotificationComponent;
import com.cyplay.atproj.asperteam.dagger.component.DaggerNotificationComponent;
import com.cyplay.atproj.asperteam.dagger.module.BandModule;
import atproj.cyplay.com.asperteamapi.dagger.module.CrossknowledgeModule;
import com.cyplay.atproj.asperteam.dagger.module.FacebookModule;
import com.cyplay.atproj.asperteam.dagger.module.NotificationModule;

import atproj.cyplay.com.asperteamapi.dagger.module.InteractorModule;
import atproj.cyplay.com.asperteamapi.dagger.module.NetModule;
import atproj.cyplay.com.asperteamapi.dagger.module.OkHttpClientModule;
import atproj.cyplay.com.asperteamapi.dagger.module.PicassoModule;
import atproj.cyplay.com.asperteamapi.dagger.module.UserSttingsModule;
import atproj.cyplay.com.asperteamapi.util.ClientUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by andre on 24-Mar-18.
 */

public class App extends Application {

    public static final String CHANNEL_ID = "bandServiceChannel";

    ApplicationComponent applicationComponent;
    UserSettingsComponent userSettingsComponent;
    OkHttpClientComponent okHttpClientComponent;
    AdminOkHttpClientComponent adminOkHttpClientComponent;
    FacebookComponent facebookComponent;
    PicassoComponent picassoComponent;
    NetComponent netComponent;
    AdminNetComponent adminNetComponent;
    InteractorComponent interactorComponent;
    ProfileAdminInteractorComponent profileAdminInteractorComponent;
    BandComponent bandComponent;
    CrossknowledgeComponent crossknowledgeComponent;
    NotificationComponent notificationComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        initLogFile();

        createNotificationChannel();

        ClientUtil.setClientUrl(BuildConfig.BUILD_TYPE, BuildConfig.HOST);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        userSettingsComponent = DaggerUserSettingsComponent.builder()
                .applicationComponent(applicationComponent)
                .userSttingsModule(new UserSttingsModule())
                .build();

        adminOkHttpClientComponent = DaggerAdminOkHttpClientComponent.builder()
                .applicationComponent(applicationComponent)
                .adminOkHttpClientModule(new AdminOkHttpClientModule(ClientUtil.getClientUrl()))
                .build();

        okHttpClientComponent = DaggerOkHttpClientComponent.builder()
                .applicationComponent(applicationComponent)
                .userSettingsComponent(userSettingsComponent)
                .okHttpClientModule(new OkHttpClientModule(ClientUtil.getClientUrl()))
                .build();

        facebookComponent = DaggerFacebookComponent.builder()
                .applicationComponent(applicationComponent)
                .okHttpClientComponent(okHttpClientComponent)
                .facebookModule(new FacebookModule())
                .build();

        picassoComponent = DaggerPicassoComponent.builder()
                .applicationComponent(applicationComponent)
                .okHttpClientComponent(okHttpClientComponent)
                .picassoModule(new PicassoModule())
                .build();

        netComponent = DaggerNetComponent.builder()
                .okHttpClientComponent(okHttpClientComponent)
                .netModule(new NetModule(ClientUtil.getClientUrl()))
                .build();

        adminNetComponent = DaggerAdminNetComponent.builder()
                .adminOkHttpClientComponent(adminOkHttpClientComponent)
                .adminNetModule(new AdminNetModule(ClientUtil.getClientUrl()))
                .build();

        interactorComponent = DaggerInteractorComponent.builder()
                .netComponent(netComponent)
                .interactorModule(new InteractorModule())
                .build();

        profileAdminInteractorComponent = DaggerProfileAdminInteractorComponent.builder()
                .adminNetComponent(adminNetComponent)
                .profileAdminInteractorModule(new ProfileAdminInteractorModule())
                .build();

        bandComponent = DaggerBandComponent.builder()
                .applicationComponent(applicationComponent)
                .userSettingsComponent(userSettingsComponent)
                .interactorComponent(interactorComponent)
                .bandModule(new BandModule(this))
                .build();

        crossknowledgeComponent = DaggerCrossknowledgeComponent.builder()
                .applicationComponent(applicationComponent)
                .userSettingsComponent(userSettingsComponent)
                .crossknowledgeModule(new CrossknowledgeModule())
                .build();

        notificationComponent = DaggerNotificationComponent.builder()
                .applicationComponent(applicationComponent)
                .notificationModule(new NotificationModule())
                .build();
    }

    public void inject(Object object) {
        AsperTeamComponent asperTeamComponent = DaggerAsperTeamComponent.builder()
                .netComponent(netComponent)
                .userSettingsComponent(userSettingsComponent)
                .picassoComponent(picassoComponent)
                .facebookComponent(facebookComponent)
                .interactorComponent(interactorComponent)
                .profileAdminInteractorComponent(profileAdminInteractorComponent)
                .bandComponent(bandComponent)
                .crossknowledgeComponent(crossknowledgeComponent)
                .notificationComponent(notificationComponent)
                .build();

        try {
            Method method = asperTeamComponent.getClass().getMethod("inject", object.getClass());
            method.invoke(asperTeamComponent, object);
        } catch (Exception e) {
        }
    }

    private void initLogFile() {
        if ( isExternalStorageWritable() ) {

            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/AsperTeam" );
            File logDirectory = new File( appDirectory + "/log" );
            File logFile = new File( logDirectory, "logcat" + System.currentTimeMillis() + ".txt" );

            // create app folder
            if ( !appDirectory.exists() ) {
                appDirectory.mkdir();
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
            }

            // clear the previous logcat and then write the new one to the file
            try {
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } else if ( isExternalStorageReadable() ) {
            // only readable
            Log.d("App", "only readable");
        } else {
            // not accessible
            Log.d("App", "not accessible");
        }
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel =    new NotificationChannel(
                    CHANNEL_ID,
                    "Band Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

}
