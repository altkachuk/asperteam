package com.cyplay.atproj.asperteam.ui.activity.base;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.band.AtBand;
import com.cyplay.atproj.asperteam.band.AtBandUtil;
import com.cyplay.atproj.asperteam.service.BandService;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.band.BandManager;

import javax.inject.Inject;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

/**
 * Created by andre on 20-Nov-18.
 */

public class BaseBandActivity extends BaseActivity {

    private final String TAG = "BaseActivity";

    @Inject
    public BandManager bandManager;

    @Inject
    UserSettingsUtil userSettings;

    @Override
    protected void onResume() {
        super.onResume();

        if (userSettings.isBandUseAgree()) {
            connect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.REQUEST_ENABLE_BT) {
            connect();
        }
    }

    protected void connect() {
        if (!AtBandUtil.isAppInstalled(getApplicationContext())) {
            return;
        }

        Log.i(TAG, "connect");

        BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            enableBluetooth();
            return;
        }

        startService();
    }

    private void startService() {
        if (!isMyServiceRunning(BandService.class)) {
            Intent bandServiceIntent = new Intent(this, BandService.class);
            bandServiceIntent.setAction(BandService.STARTFOREGROUND_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(bandServiceIntent);
            } else {
                startService(bandServiceIntent);
            }

            bandManager.registerListener(this);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    protected void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivityForResult(enableBtIntent, RequestCode.REQUEST_ENABLE_BT);
    }

    protected void showAppNotInstalledDialog() {
        getPopup().initPopup(RequestCode.BAND_NOT_CONNECTED_REQUEST,
                getString(R.string.band_not_connected_title),
                getString(R.string.band_not_connected_description));
        getPopup().setImage(R.drawable.img_braccelet_not);
        getPopup().setPositive(getString(R.string.agree_button));
        getPopup().setNegative(getString(R.string.disagree_button));
        getPopup().show();

        Log.d("BandPairFragment", "Microsoft Health App is not installed");
    }
}
