package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.band.BandListener;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.ui.fragment.StressHomeFragment;
import com.cyplay.atproj.asperteam.band.detector.StressDetector;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

/**
 * Created by andre on 30-Mar-18.
 */

public class HomeActivity extends BaseMenuActivity {

    @Inject
    UserSettingsUtil userSettings;

    StressHomeFragment stressHomeFragment;

    BandListener _bandManagerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        stressHomeFragment = (StressHomeFragment) getFragmentManager().findFragmentById(R.id.stressHomefragment);
    }

    @Override
    protected void onPause() {
        super.onPause();

        band.removeListener(_bandManagerListener);
        band.gotoIdle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Activity activity = this;
        band.addListener(new BandListener() {
            @Override
            public void onDataUpdate(StressDetector.StressData event) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int stressLevel = (int) (event.stressLevel * 100.0f);
                        stressHomeFragment.onNewStressLevel(stressLevel);
                    }
                });
            }

            @Override
            public void onStressDetected(StressDetector.StressData event) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int stressLevel = (int) (event.stressLevel * 100.0f);
                        stressHomeFragment.onStress(stressLevel, event.rri);
                    }
                });
            }

            @Override
            public void onUpdate() {
                ;
            }
        });
        band.gotoActive();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int res = grantResults[i];

            if (permission.equals(android.Manifest.permission.ACCESS_FINE_LOCATION) && res == PackageManager.PERMISSION_GRANTED) {
                stressHomeFragment.sendStress();
            } else if (permission.equals(android.Manifest.permission.ACCESS_FINE_LOCATION) && res != PackageManager.PERMISSION_GRANTED) {
                //stressHomeFragment.sendValidateByUserMessage();
            }
        }
    }

}
