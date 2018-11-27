package com.cyplay.atproj.asperteam.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by andre on 26-Nov-18.
 */

public class BandRestarterBroadcastReciever extends BroadcastReceiver {

    public final String TAG = "BandRestarterBroadcastR";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Service Stop");
        context.startService(new Intent(context, BandService.class));
    }
}
