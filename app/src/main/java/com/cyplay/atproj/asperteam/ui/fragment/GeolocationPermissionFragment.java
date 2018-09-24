package com.cyplay.atproj.asperteam.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseFragment;
import com.cyplay.atproj.asperteam.ui.fragment.base.BasePopupFragment;

import javax.inject.Inject;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 20-Jun-18.
 */

public class GeolocationPermissionFragment extends BaseFragment {



    @Inject
    UserSettingsUtil userSettings;

    @BindView(R.id.geolocationButton)
    ImageView geolocationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_geolocation_permission, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateGeolocationButton();
    }

    @OnClick(R.id.geolocationButton)
    public void onClickGeolocationButton() {
        getPopup().initPopup(RequestCode.GEOLOCATION_REQUEST,
                getString(R.string.geolocation_permission_title),
                getString(R.string.geolocation_permission_description));
        getPopup().setPositive(getString(R.string.yes));
        getPopup().setNegative(getString(R.string.no));
        getPopup().setOnPositiveClickListener(onPositiveClickListener);
        getPopup().show();
    }

    protected BasePopupFragment.OnPositiveClickListener onPositiveClickListener = new BasePopupFragment.OnPositiveClickListener() {
        @Override
        public void onClick(int requestCode) {
            if (requestCode == RequestCode.GEOLOCATION_REQUEST) {
                getPopup().hide();
                userSettings.setGeolocationSending(!userSettings.isGeolocationSending());
                updateGeolocationButton();
            }
        }
    };

    private void updateGeolocationButton() {
        if (userSettings.isGeolocationSending())
            geolocationButton.setImageResource(R.drawable.ic_switch_on);
        else
            geolocationButton.setImageResource(R.drawable.ic_switch_off);
    }
}
