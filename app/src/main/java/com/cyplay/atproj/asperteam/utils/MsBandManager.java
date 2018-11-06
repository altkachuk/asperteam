package com.cyplay.atproj.asperteam.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.HeartRateConsentListener;

import java.util.List;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Stress;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import goosante.neogia.xyz.stresslibrary.model.AsperteamRmssdStressLevelCalculator;
import goosante.neogia.xyz.stresslibrary.model.AsperteamStressListener;
import goosante.neogia.xyz.stresslibrary.model.Band;
import goosante.neogia.xyz.stresslibrary.model.MsBand;

/**
 * Created by andre on 24-Apr-18.
 */

public class MsBandManager implements AsperteamStressListener {

    private final static int time_delay = 5 * 60 * 1000; // 5 minutes

    private Context _context;

    private boolean _bandUseAgree;
    private StressInteractor _stressInteractor;
    private String _id;
    private boolean _isGeolocationSending;

    private AsperteamRmssdStressLevelCalculator _calculator;
    private boolean _created = false;
    private boolean _active = false;
    private boolean _calibrated = false;

    private int _lastAppStressLevel;
    private float _lastRmssd;
    private int _lastRri;

    private boolean _isDelayed = false;

    private boolean _needStressNotified = false;

    private LocationManager _locationManager;

    private MsBandManagerListener _listener;

    public MsBandManager(Context context) {
        _context = context;

        _calculator = new AsperteamRmssdStressLevelCalculator();
        _calculator.addStressListener(this);

        _locationManager = LocationManager.getInstance(_context);
        _locationManager.setListener(onLocationManagerListener);

        _created = true;
    }

    public void setStressInteractor(StressInteractor stressInteractor) {
        _stressInteractor = stressInteractor;
    }

    public void setId(String id) {
        _id = id;
    }

    public void setGeolocationSending(Boolean value) {
        _isGeolocationSending = value;
    }

    public void setLevelMax(float value) {
        float levelMax = value / 100f;
        _calculator.setMaxStressLevel(levelMax);
    }

    public void setBandUseAgree(boolean value) {
        _bandUseAgree = value;
    }

    public boolean isConnected() {
        return Band.getSelectedBand() != null;
    }

    public boolean isCalibrated() {
        return _calibrated;
    }

    public boolean connect(Activity activity) {
        if (!_bandUseAgree) {
            if (_listener != null)
                _listener.onPermissionDenied();
            return false;
        }

        if (!isAppInstalled("com.microsoft.kapp")) {
            if (_listener != null)
                _listener.onAppNotInstalled();
            return false;
        }

        BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            enableBluetooth(activity);
            return false;
        }

        if (!isConnected())
            connectBand(activity);

        return true;
    }

    private void enableBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, RequestCode.REQUEST_ENABLE_BT);
    }

    private void connectBand(final Activity activity) {
        if (isAppInstalled("com.microsoft.kapp") && MsBand.getInstance() != null && !isConnected()) {
            MsBand band = MsBand.getInstance();
            band.connect(activity);
            Band.setSelectedBand(band);
        }
    }

    public void start(Activity activity) {
        if (!_bandUseAgree) {
            if (_listener != null)
                _listener.onPermissionDenied();
        }

        if (connect(activity)) {
            final Activity act = activity;
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startBand(act);
                }
            }, 250);
        }
    }

    private void startBand(Activity activity) {
        if (isConnected() && !_active) {
            MsBand msBand = (MsBand) Band.getSelectedBand();
            if (msBand.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
                msBand.getSensorManager().requestHeartRateConsent(activity, mHeartRateConsentListener);
            } else {
                _calculator.calibrate();
                _calculator.start();
                _active = true;
            }
        }
    }

    private HeartRateConsentListener mHeartRateConsentListener = new HeartRateConsentListener() {
        @Override
        public void userAccepted(boolean b) {
            _calculator.calibrate();
            _calculator.start();
            _active = true;
        }
    };

    public void stop() {
        if (isConnected() && _active) {
            _calculator.stop();
            _active = false;
        }
    }

    public void setListener(MsBandManagerListener listener) {
        _listener = listener;
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = _context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @Override
    public void onCalibrated() {
        if (isAppOnForeground(_context)) {
            if (_listener != null)
                _listener.onCalibrated();
        }
        _calibrated = true;
    }

    @Override
    public void onStress(float stressLevel, float rmssd, int rri) {
        if (!_isDelayed) {
            int appLevel = (int)(stressLevel*100.0f);
            if (isAppOnForeground(_context)) {
                if (_listener != null)
                    _listener.onStress(appLevel, rmssd, rri);
                startDelayTimer();
            } else {
                _needStressNotified = true;
                _lastAppStressLevel = appLevel;
                _lastRmssd = rmssd;
                _lastRri = rri;
                NotificationUtil.sendNotification(_context, R.string.notification_title, R.string.notification_stress, HomeActivity.class);
            }
        }
    }

    @Override
    public void onNewStressLevel(float stressLevel, float rmssd, int rri) {
        int appLevel = (int)(stressLevel*100.0f);
        if (_stressInteractor != null) {
            addStress(_id, appLevel, rri, 0.0f, 0.0f);
        }

        if (isAppOnForeground(_context)) {
            if (_listener != null)
                _listener.onNewStressLevel(appLevel, rmssd, rri);
            if (_needStressNotified) {
                if (_listener != null)
                    _listener.onStress(_lastAppStressLevel, _lastRmssd, _lastRri);
                _needStressNotified = false;
                startDelayTimer();
            }
        }
    }

    private void startDelayTimer() {
        _isDelayed = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _isDelayed = false;
            }
        }, time_delay);
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

    private LocationManager.OnLocationManagerListener onLocationManagerListener = new LocationManager.OnLocationManagerListener() {
        @Override
        public void onDetectLocation(int stressLevel, int rri, Location location) {
            if (_isGeolocationSending)
                addStress(_id, stressLevel, rri, location.getLatitude(), location.getLongitude());
        }
    };



    private void addStress(String userId, int level, int rri, double lat, double lng) {
        _stressInteractor.addStress(userId, level, rri, lat, lng, new ResourceRequestCallback<Stress>() {
            @Override
            public void onSucess(Stress stress) {

            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    public interface MsBandManagerListener {
        void onCalibrated();
        void onNewStressLevel(int stressLevel, float rmssd, int rri);
        void onStress(int stressLevel, float rmssd, int rri);
        void onAppNotInstalled();
        void onPermissionDenied();
    }
}
