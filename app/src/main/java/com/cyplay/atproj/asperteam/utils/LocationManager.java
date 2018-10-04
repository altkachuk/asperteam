package com.cyplay.atproj.asperteam.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andre on 11-Jun-18.
 */

public class LocationManager extends Service implements LocationListener {


    private static LocationManager instance;

    private Context _context;
    private android.location.LocationManager _locationManager;
    private int _stressLevel;
    private int _rri;
    private Location _lastLocation;
    private Timer _timer;
    private TimerTask _timerTask;
    private OnLocationManagerListener _listener;

    public static LocationManager getInstance(Context context) {
        if (instance == null)
            instance = new LocationManager(context);

        return instance;
    }

    private LocationManager(Context context) {
        _context = context;
        _locationManager = (android.location.LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
        _timer = new Timer();

    }

    public void setListener(OnLocationManagerListener listener) {
        _listener = listener;
    }

    public void start(int stressLevel, int rri) {
        _stressLevel = stressLevel;
        _rri = rri;
        _lastLocation = null;
        startTimer();
        if (ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            _locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 50, 0, this);
            _locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 50, 0, this);
        }
    }

    public void stop() {
        stopTimer();
        if (ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            _locationManager.removeUpdates(this);
        }
    }

    private void startTimer() {
        _timerTask = new TimerTask() {
            @Override
            public void run() {
                stop();
                if (_listener != null) {
                    if (_lastLocation == null) {
                        try {
                            List<String> providers = _locationManager.getProviders(true);
                            Location bestLocation = null;
                            for (String provider : providers) {
                                Location l = _locationManager.getLastKnownLocation(provider);
                                if (l == null) {
                                    continue;
                                }
                                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                    // Found best last known location: %s", l);
                                    bestLocation = l;
                                }
                            }
                            _listener.onDetectLocation(_stressLevel, _rri, bestLocation);
                        } catch (SecurityException e) {};
                    } else {
                        _listener.onDetectLocation(_stressLevel, _rri, _lastLocation);
                    }
                }
            }
        };

        _timer.schedule(_timerTask, 5000);
    }

    private void stopTimer() {
        if (_timerTask != null) {
            _timerTask.cancel();
            _timerTask = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(android.location.LocationManager.GPS_PROVIDER)) {
            _lastLocation = location;
            stop();
            if (_listener != null) {
                _listener.onDetectLocation(_stressLevel, _rri, _lastLocation);
            }
        } else {
            if (_lastLocation == null)
                _lastLocation = location;
            else if (_lastLocation.getAccuracy() > location.getAccuracy())
                _lastLocation = location;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public interface OnLocationManagerListener {
        void onDetectLocation(int stressLevel, int rri, Location location);
    }
}
