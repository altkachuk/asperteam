package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.service.BandService;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseBandMenuActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.ui.fragment.StressHomeFragment;
import com.cyplay.atproj.asperteam.utils.BandManager;
import com.cyplay.atproj.asperteam.utils.MsBandManager;
import com.cyplay.atproj.asperteam.utils.StressDetector;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

/**
 * Created by andre on 30-Mar-18.
 */

public class HomeActivity extends BaseBandMenuActivity {

    @Inject
    UserSettingsUtil userSettings;

    StressHomeFragment stressHomeFragment;

    BandService mSensorService;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSensorService = new BandService(this.getApplicationContext());
        mServiceIntent = new Intent(this.getApplicationContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        stressHomeFragment = (StressHomeFragment) getFragmentManager().findFragmentById(R.id.stressHomefragment);

        final Activity activity = this;

        bandManager.setListener(new BandManager.BandManagerListener() {
            @Override
            public void onNewStressLevel(StressDetector.StressEvent event) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int stressLevel = (int) (event.stressLevel * 100.0f);
                        stressHomeFragment.onNewStressLevel(stressLevel);
                    }
                });
            }

            @Override
            public void onStress(StressDetector.StressEvent event) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int stressLevel = (int) (event.stressLevel * 100.0f);
                        stressHomeFragment.onStress(stressLevel, event.rri);
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int res = grantResults[i];

            if (permission.equals(android.Manifest.permission.ACCESS_FINE_LOCATION) && res == PackageManager.PERMISSION_GRANTED) {
                stressHomeFragment.sendStress();
                stressHomeFragment.sendValidateByUserMessage();
            } else if (permission.equals(android.Manifest.permission.ACCESS_FINE_LOCATION) && res != PackageManager.PERMISSION_GRANTED) {
                stressHomeFragment.sendValidateByUserMessage();
            }
        }
    }

}
