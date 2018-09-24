package com.cyplay.atproj.asperteam.ui.activity;

import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseActivity;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.utils.MsBandManager;

import javax.inject.Inject;

/**
 * Created by andre on 02-Apr-18.
 */

public class BandActivity extends BaseActivity {

    @Inject
    MsBandManager bandManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        connectBand();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.REQUEST_ENABLE_BT) {
            connectBand();
        }
    }

    private void connectBand() {
        if (bandManager.connect(this)) {
            final ProgressDialog dialog = ProgressDialog.show(
                    this,
                    getString(R.string.band_connection_title),
                    getString(R.string.band_connection_message),
                    true);

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    getPopup().initPopup(RequestCode.BAND_CONNECTED_REQUEST,
                            getString(R.string.band_connected_title),
                            getString(R.string.band_connected_description));
                    getPopup().setImage(R.drawable.img_braccelet_ok);
                    getPopup().setPositive(getString(R.string.ok_button));
                    getPopup().show();
                }
            }, 3000);
        } else {

        }
    }

    @Override
    protected void onAppNotInstalled() {
        getPopup().initPopup(RequestCode.BAND_NOT_CONNECTED_REQUEST,
                getString(R.string.band_not_connected_title),
                getString(R.string.band_not_connected_description));
        getPopup().setImage(R.drawable.img_braccelet_not);
        getPopup().setPositive(getString(R.string.agree_button));
        getPopup().setNegative(getString(R.string.disagree_button));
        getPopup().show();

        Log.d("BandPairFragment", "Microsoft Health App is not installed");
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.BAND_CONNECTED_REQUEST:
                startProfileActivity();
                break;

            case RequestCode.BAND_NOT_CONNECTED_REQUEST:
                connectBand();
                break;
        }
    }

    @Override
    protected void onPopupNegativeClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.BAND_NOT_CONNECTED_REQUEST:
                startProfileActivity();
                break;
        }
    }

    private void startProfileActivity() {
        Intent profileIntent = new Intent(this.getApplicationContext(), ProfileActivity.class);
        TaskStackBuilder.create(this)
                .addParentStack(ProfileActivity.class)
                .addNextIntent(profileIntent)
                .startActivities();
    }
}
