package com.cyplay.atproj.asperteam.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;

import java.util.List;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Stress;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by andre on 20-Nov-18.
 */

public class BandManager {

    public final String TAG = "BandManager";
    private final static int time_delay = 5 * 60 * 1000; // 5 minutes

    private Context _context;
    private StressInteractor _stressInteractor;
    private LocationManager _locationManager;
    private String _id;
    private float _levelMax;
    private BandManagerListener _listener;
    private StressDetector.StressEvent _stressEvent;

    private Observable<StressDetector.StressEvent> stressObservable;
    private Disposable stressDisposable;

    private boolean _isDelayed = false;
    private boolean _notified = false;

    public BandManager(Context context) {
        _context = context;
        _locationManager = LocationManager.getInstance(context);
        _locationManager.setListener(new LocationManager.OnLocationManagerListener() {
            @Override
            public void onDetectLocation(int stressLevel, int rri, Location location) {
                addStress(_id, stressLevel, rri, location.getLatitude(), location.getLongitude());
            }
        });

        stressObservable = StressDetector.create(context);
    }

    public void setStressInteractor(StressInteractor stressInteractor) {
        _stressInteractor = stressInteractor;
    }

    public void setId(String id) {
        _id = id;
    }

    public void setLevelMax(float value) {
        _levelMax = value / 100f;
    }

    public void setListener(BandManagerListener listener) {
        _listener = listener;
    }

    public void start() {
        if (stressDisposable == null || stressDisposable.isDisposed()) {
            stressDisposable = stressObservable.subscribe(
                    stressEvent -> {
                        Log.d(TAG, stressEvent.toString());
                        int stressLevel = (int) (stressEvent.stressLevel * 100.0f);
                        addStress(_id, stressLevel, stressEvent.rri, 0.0f, 0.0f);

                        if (!_isDelayed) {
                            if (stressEvent.stressLevel > _levelMax) {
                                _stressEvent = stressEvent;
                            }
                        }

                        if (_listener == null)
                            return;

                        if (isAppOnForeground(_context)) {
                            _notified = false;
                            _listener.onNewStressLevel(stressEvent);

                            if (_stressEvent != null) {
                                _listener.onStress(_stressEvent);
                                startDelayTimer();
                            }

                        } else {
                            if (_stressEvent != null && !_notified) {
                                _notified = true;
                                NotificationUtil.sendNotification(_context, R.string.notification_title, R.string.notification_stress, HomeActivity.class);
                            }
                        }
                    }
            );
        }
    }

    public void stop() {
        stressDisposable.dispose();
    }

    private void startDelayTimer() {
        _isDelayed = true;
        _stressEvent = null;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _isDelayed = false;
            }
        }, time_delay);
    }

    private void addStress(String userId, int level, int rri, double lat, double lng) {
        _stressInteractor.addStress(userId, level, rri, lat, lng, new ResourceRequestCallback<Stress>() {
            @Override
            public void onSucess(Stress stress) {
                ;
            }

            @Override
            public void onError(BaseException e) {
                ;
            }
        });
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public interface BandManagerListener {
        void onNewStressLevel(StressDetector.StressEvent event);
        void onStress(StressDetector.StressEvent event);
    }
}
