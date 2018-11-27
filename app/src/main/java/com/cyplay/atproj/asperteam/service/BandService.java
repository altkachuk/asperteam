package com.cyplay.atproj.asperteam.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cyplay.atproj.asperteam.dagger.App;
import com.cyplay.atproj.asperteam.utils.BandManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andre on 26-Nov-18.
 */

public class BandService extends Service {

    public final String TAG = "BandService";
    private Context _context;

    public BandService(Context context) {
        super();
        _context = context;
        Log.d(TAG, "start service");
    }

    public BandService() {
        ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ondestroy");

        Intent broadcastIntent = new Intent(this, BandRestarterBroadcastReciever.class);
        sendBroadcast(broadcastIntent);
        stop();
    }

    public void start() {
        App app = (App) getApplication();
        BandManager bandManager = new BandManager(getApplicationContext());
        bandManager.setStressInteractor(app.interactorComponent.getStressInteractor());
        bandManager.setId(app.userSettingsComponent.getUserSettings().getId());
        bandManager.setLevelMax(app.userSettingsComponent.getUserSettings().getStressLevelMax());
    }

    public void stop() {
        ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
