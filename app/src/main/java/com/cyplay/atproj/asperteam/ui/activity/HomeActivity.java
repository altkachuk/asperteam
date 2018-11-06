package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.ui.fragment.StressHomeFragment;
import com.cyplay.atproj.asperteam.utils.MsBandManager;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

/**
 * Created by andre on 30-Mar-18.
 */

public class HomeActivity extends BaseMenuActivity {

    @Inject
    MsBandManager bandManager;

    @Inject
    UserSettingsUtil userSettings;

    StressHomeFragment stressHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        stressHomeFragment = (StressHomeFragment) getFragmentManager().findFragmentById(R.id.stressHomefragment);

        final Activity activity = this;

        bandManager.setListener(new MsBandManager.MsBandManagerListener() {
            @Override
            public void onCalibrated() {

            }

            @Override
            public void onNewStressLevel(final int stressLevel, float rmssd, int rri) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stressHomeFragment.onNewStressLevel(stressLevel);
                    }
                });
            }

            @Override
            public void onStress(final int stressLevel, float rmssd, final int rri) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stressHomeFragment.onStress(stressLevel, rri);
                    }
                });
            }

            @Override
            public void onAppNotInstalled() {

            }

            @Override
            public void onPermissionDenied() {

            }
        });
        bandManager.start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.REQUEST_ENABLE_BT) {
            bandManager.start(this);
        }
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
