package com.cyplay.atproj.asperteam.band;

import android.app.Activity;
import android.content.Context;

import com.cyplay.atproj.asperteam.band.detector.StressDetector;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;

/**
 * Created by andre on 21-Jan-19.
 */

public class BandManager implements IBand {

    private HashMap<BandListener, BandListener> _listeners = new HashMap<>();
    private Timer timer;
    private int counter;


    public BandManager(Context context) {
        timer = new Timer();
        counter = 0;
    }

    @Override
    public void init(StressInteractor stressInteractor, String id, float levelMax) {
        ;
    }

    @Override
    public void addListener(BandListener listener) {
        if (_listeners.get(listener) == null) {
            _listeners.put(listener, listener);
        }
    }

    @Override
    public void removeListener(BandListener listener) {
        if (_listeners.get(listener) != null) {
            _listeners.remove(listener);
        }
    }

    @Override
    public void registerListener(Activity activity) {

    }

    @Override
    public void registerListener() {

    }

    @Override
    public void gotoIdle() {

    }

    @Override
    public void gotoActive() {

    }

    @Override
    public void subscribe() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                counter++;
                for (BandListener listener : _listeners.keySet()) {
                    listener.onUpdate();
                    StressDetector.StressData stressData = new StressDetector.StressData(600, 0.8f, 25.0f);
                    listener.onDataUpdate(stressData);

                    if (counter > 30) {
                        listener.onStressDetected(stressData);
                    }
                }

                if (counter > 30) counter = 0;
            }
        }, 1000, 1000);
    }

    @Override
    public void connect() {

    }

    @Override
    public void stop() {
        timer.cancel();
    }
}
