package com.cyplay.atproj.asperteam.band;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.cyplay.atproj.asperteam.utils.ApplicationUtil;
import com.cyplay.atproj.asperteam.utils.LocationManager;
import com.cyplay.atproj.asperteam.utils.NotificationUtil;
import com.cyplay.atproj.asperteam.band.detector.StressDetector;

import java.util.HashMap;
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

    private final String TAG = "BandManager";
    private final static int time_delay = 5 * 60 * 1000; // 5 minutes

    private Context _context;

    private AtBand _band;
    private LocationManager _locationManager;
    private Observable<StressDetector.StressData> stressObservable;
    private Disposable stressDisposable;

    private StressInteractor _stressInteractor;
    private String _id;
    private float _levelMax;

    private HashMap<BandManagerListener, BandManagerListener> _listeners = new HashMap<>();

    private boolean _isIdle = false;

    public BandManager(Context context) {
        _context = context;
        _band = AtBand.getInstance();
        _locationManager = LocationManager.getInstance(context);
        _locationManager.setListener( (stressLevel, rri, location) ->{
            addStress(_id, stressLevel, rri, location.getLatitude(), location.getLatitude());
        });
        stressObservable = StressDetector.create(context);
    }

    public void init(StressInteractor stressInteractor, String id, float levelMax) {
        _stressInteractor = stressInteractor;
        _id = id;
        _levelMax = levelMax / 100f;
    }

    public void addListener(BandManagerListener listener) {
        if (_listeners.get(listener) == null) {
            _listeners.put(listener, listener);
        }
    }

    public void removeListener(BandManagerListener listener) {
        if (_listeners.get(listener) != null) {
            _listeners.remove(listener);
        }
    }

    public void subscribe() {
        Log.i(TAG, "subscribe");
        if (stressDisposable == null || stressDisposable.isDisposed()) {
            stressDisposable = stressObservable.subscribe(
                stressData -> {
                    Log.i(TAG, stressData.toString());
                    for (BandManagerListener listener : _listeners.keySet()) {
                        listener.onUpdate();
                    }

                    int stressLevel = (int) (stressData.stressLevel * 100.0f);
                    addStress(_id, stressLevel, stressData.rri, 0.0f, 0.0f);

                    if (!_isIdle && stressData.stressLevel > _levelMax) {
                        stressDetected(stressData);
                    }

                    if (ApplicationUtil.isAppOnForeground(_context)) {
                        for (BandManagerListener listener : _listeners.keySet()) {
                            listener.onDataUpdate(stressData);
                        }
                    }
                }
            );
        }
    }

    public void connect() {
        if (_band.isClientExist(_context)) {
            _band.connect();
        }
    }

    public void registerListener(Activity activity) {
        if (_band.isClientExist(_context)) {
            _band.registerListener(activity);
        }
    }

    public void stop() {
        _band.disconnect();
        stressDisposable.dispose();
    }

    private void stressDetected(StressDetector.StressData stressData) {
        if (ApplicationUtil.isAppOnForeground(_context)) {
            for (BandManagerListener listener : _listeners.keySet()) {
                listener.onStressDetected(stressData);
            }
        } else {
            NotificationUtil.sendNotification(_context, R.string.notification_title, R.string.notification_stress, HomeActivity.class);
        }
        reset();
    }

    private void reset() {
        _isIdle = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _isIdle = false;
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

    public interface BandManagerListener {
        void onDataUpdate(StressDetector.StressData stressData);
        void onStressDetected(StressDetector.StressData stressData);
        void onUpdate();
    }
}
