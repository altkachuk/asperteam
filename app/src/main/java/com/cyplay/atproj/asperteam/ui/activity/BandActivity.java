package com.cyplay.atproj.asperteam.ui.activity;

import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseBandActivity;
import com.cyplay.atproj.asperteam.band.BandManager;

import javax.inject.Inject;

/**
 * Created by andre on 02-Apr-18.
 */

public class BandActivity extends BaseBandActivity {

    @Inject
    BandManager bandManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void connect() {
        super.connect();

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
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.BAND_CONNECTED_REQUEST:
                startProfileActivity();
                break;

            case RequestCode.BAND_NOT_CONNECTED_REQUEST:
                connect();
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
