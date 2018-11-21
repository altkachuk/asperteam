package com.cyplay.atproj.asperteam.ui.activity.base;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.utils.BandManager;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.HeartRateConsentListener;

import javax.inject.Inject;

import goosante.neogia.xyz.stresslibrary.model.Band;
import goosante.neogia.xyz.stresslibrary.model.MsBand;

/**
 * Created by andre on 20-Nov-18.
 */

public class BaseBandMenuActivity extends BaseMenuActivity {

    @Inject
    public BandManager bandManager;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (userSettings.isBandUseAgree()) {
            connect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.REQUEST_ENABLE_BT) {
            bandManager.start();
        }
    }

    private void connect() {
        if (!isAppInstalled("com.microsoft.kapp")) {
            return;
        }

        BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            enableBluetooth();
            return;
        }

        connectBand(this);
    }

    protected void connectBand(final Activity activity) {
        if (isAppInstalled("com.microsoft.kapp") && MsBand.getInstance() != null) {
            MsBand band = MsBand.getInstance();
            band.connect(activity);
            Band.setSelectedBand(band);

            final Activity act = activity;
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    MsBand msBand = (MsBand) Band.getSelectedBand();
                    if (msBand.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
                        msBand.getSensorManager().requestHeartRateConsent(activity, new HeartRateConsentListener() {
                            @Override
                            public void userAccepted(boolean b) {
                                bandManager.start();
                            }
                        });
                    } else {
                        bandManager.start();
                    }
                }
            }, 250);
        }
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getApplicationContext().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivityForResult(enableBtIntent, RequestCode.REQUEST_ENABLE_BT);
    }
}
