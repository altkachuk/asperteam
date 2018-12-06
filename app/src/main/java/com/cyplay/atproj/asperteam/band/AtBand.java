package com.cyplay.atproj.asperteam.band;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandRRIntervalEvent;
import com.microsoft.band.sensors.BandRRIntervalEventListener;
import com.microsoft.band.sensors.BandSensorManager;
import com.microsoft.band.sensors.HeartRateConsentListener;

/**
 * Created by andre on 30-Nov-18.
 */

public class AtBand implements BandRRIntervalEventListener {

    private final String TAG = "AtBand";
    private final static AtBand instance = new AtBand();

    private AtBandListener _listener;
    private BandClient _client;

    AtBand() {
        ;
    }

    public static AtBand getInstance() {
        return instance;
    }

    public void setListener(AtBandListener listener) {
        _listener = listener;
    }

    public boolean isClientExist(Context context) {
        if (_client != null) {
            return true;
        }
        BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
        if (devices.length > 0) {
            _client = BandClientManager.getInstance().create(context, devices[0]);
            return true;
        }
        return false;
    }

    public void connect() {
        Log.i(TAG, "connect");

        connectBand();
    }

    public void disconnect() {
        try {
            _client.disconnect();
            _client.getSensorManager().unregisterRRIntervalEventListener(AtBand.getInstance());
        } catch (BandIOException e) {
            ;
        }
    }

    public void registerListener(Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (_client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                    registerRRIListener();
                } else {
                    requestHeartRateConsent(activity);
                }
            }
        }, 1000);
    }

    public void registerListener() {
        registerRRIListener();
    }

    private void connectBand() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "connectBand");
                    _client.connect().await();
                } catch (InterruptedException e) {
                    Log.e(TAG, "connectBand:" + e.getMessage());
                    Log.d(TAG, "InterruptedException");
                } catch (BandException e) {
                    Log.e(TAG, "connectBand:" + e.getMessage());
                    Log.d(TAG, "BandException");
                }
            }
        });
        thread.start();
    }

    private void requestHeartRateConsent(Activity activity) {
        Thread thread = new Thread(
            () -> {
                try {
                    if (AtBand.getInstance().isConnected()) {
                        _client.getSensorManager().requestHeartRateConsent(activity, (consentGiven) -> {registerRRIListener();});
                    }
                } catch (Exception e) {
                    Log.e(TAG, "requestHeartRateConsent:" + e.getMessage());
                }
            });
        thread.start();
    }

    private boolean isConnected() {
        return (_client != null && _client.getConnectionState() == ConnectionState.CONNECTED);
    }

    private void registerRRIListener() {
        try {
            _client.getSensorManager().registerRRIntervalEventListener(AtBand.getInstance());
        } catch (Exception e) {
            Log.e(TAG, "registerRRIListener:" + e.getMessage());
        }
    }

    @Override
    public void onBandRRIntervalChanged(BandRRIntervalEvent bandRRIntervalEvent) {
        if (_listener != null) {
            _listener.onRecieve((int)(bandRRIntervalEvent.getInterval() * 1000));
        }
    }

    public interface AtBandListener {
        void onRecieve(int rri);
    }
}
