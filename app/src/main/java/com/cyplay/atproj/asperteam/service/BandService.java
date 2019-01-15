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
    private TimerDelay _timerDelay;

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
        if (intent != null && intent.getAction().equals(STARTFOREGROUND_ACTION)) {
            _timerDelay = new TimerDelay(60 * 1000, 45 * 1000, 3);

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
                    _timerDelay.update();
                }
            };
            bandManager.addListener(_bandManagerListener);

            _timerDelay.setListener(new TimerDelayListener() {
                @Override
                public void onDelay() {
                    Log.i(TAG, "Delay");
                    // try to reconnect Bluetooth
                    bandManager.subscribe();
                    bandManager.connect();
                    bandManager.registerListener();
                }

                @Override
                public void onLongDelay() {
                    Log.i(TAG, "Long Delay");
                    stopForegroundService();
                }
            });
            _timerDelay.start();

        } else if (intent != null && intent.getAction().equals(STOPFOREGROUND_ACTION)) {
            stopForegroundService();
        }

        return START_STICKY;
    }

    private void stopForegroundService() {
        _timerDelay.stop();
        bandManager.removeListener(_bandManagerListener);
        bandManager.stop();
        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class TimerDelay {

        private Timer _timer;
        private long _delay;
        private long _period;
        private int _maxDelayCnt;
        private long _lastUpdated;
        private int _delayCnt;

        private TimerDelayListener _listener;

        public TimerDelay(long delay, long period, int maxDelayCnt) {
            _timer = new Timer();
            _delay = delay;
            _period = period;
            _maxDelayCnt = maxDelayCnt;
        }

        public void setListener(TimerDelayListener listener) {
            _listener = listener;
        }

        public void update() {
            reset();
        }

        public void start() {
            reset();
            _timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - _lastUpdated > _delay) {
                        if (_listener != null) {
                            _listener.onDelay();
                        }
                        _delayCnt++;
                        if (_delayCnt >= _maxDelayCnt) {
                            _delayCnt = 0;
                            if (_listener != null) {
                                _listener.onLongDelay();
                            }
                        }
                    }
                }
            }, _delay, _period);
        }

        public void stop() {
            reset();
            _timer.cancel();
        }

        private void reset() {
            _lastUpdated = System.currentTimeMillis();
            _delayCnt = 0;
        }

    }

    interface TimerDelayListener {
        void onDelay();
        void onLongDelay();
    }
}
