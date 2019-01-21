package com.cyplay.atproj.asperteam.band;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by andre on 21-Jan-19.
 */

public class BandConnectionRule {

    static public Permission getConnectionPermission(Context context) {
        if (!AtBandUtil.isAppInstalled(context)) {
            return Permission.NOT_ISTALLED;
        }

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            return Permission.BLUETOOTH_DISABLED;
        }

        return Permission.ACCEPT;
    }

    public enum Permission {
        ACCEPT,
        NOT_ISTALLED,
        BLUETOOTH_DISABLED
    }
}
