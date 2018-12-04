package com.cyplay.atproj.asperteam.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.dagger.App;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.cyplay.atproj.asperteam.band.BandManager;
import com.cyplay.atproj.asperteam.band.detector.StressDetector;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Created by andre on 27-Nov-18.
 */

public class BandService extends Service {

    public static final String STARTFOREGROUND_ACTION = "STARTFOREGROUND_ACTION";
    public static final String STOPFOREGROUND_ACTION = "STOPFOREGROUND_ACTION";
    public static final int ONGOING_NOTIFICATION_ID = 101;

    private final String TAG = "BandService";

    @Inject
    BandManager bandManager;

    private BandManager.BandManagerListener _bandManagerListener;
    private Timer _timer;
    private long _lastUpdated = System.currentTimeMillis();

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    private void injectDependencies() {
        App.get(this).inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(STARTFOREGROUND_ACTION)) {

            Intent stopIntent = new Intent(this, BandService.class);
            stopIntent.setAction(STOPFOREGROUND_ACTION);
            PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, 0);

            Intent notificationIntent = new Intent(this, HomeActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification notification =
                        new Notification.Builder(this, App.CHANNEL_ID)
                                .setContentTitle(getText(R.string.foreground_notification_title))
                                .setContentText(getText(R.string.foreground_notification_message))
                                .setSmallIcon(R.drawable.notification_icon)
                                .setContentIntent(pendingIntent)
                                .setTicker(getText(R.string.foreground_ticker_text))
                                .addAction(R.drawable.ic_settings, "Stop", pendingStopIntent)
                                .setPriority(Notification.PRIORITY_LOW)
                                .build();
                startForeground(ONGOING_NOTIFICATION_ID, notification);
            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setContentTitle(getText(R.string.foreground_notification_title))
                        .setContentText(getText(R.string.foreground_notification_message))
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentIntent(pendingIntent)
                        .addAction(R.drawable.ic_settings, "Stop", pendingStopIntent)
                        .setPriority(NotificationCompat.PRIORITY_LOW);
                startForeground(ONGOING_NOTIFICATION_ID, notificationBuilder.build());
            }


            bandManager.subscribe();
            bandManager.connect();

            _bandManagerListener = new BandManager.BandManagerListener() {
                @Override
                public void onDataUpdate(StressDetector.StressData event) {

                }

                @Override
                public void onStressDetected(StressDetector.StressData event) {

                }

                @Override
                public void onUpdate() {
                    _lastUpdated = System.currentTimeMillis();
                }
            };
            bandManager.addListener(_bandManagerListener);

            _timer = new Timer();
            _timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - _lastUpdated > 60 * 1000) {
                        Log.i(TAG, "Long Delay");
                    }
                }
            }, 60 * 1000, 60*1000);

        } else if (intent.getAction().equals(STOPFOREGROUND_ACTION)) {
            _timer.cancel();
            bandManager.removeListener(_bandManagerListener);
            bandManager.stop();
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
