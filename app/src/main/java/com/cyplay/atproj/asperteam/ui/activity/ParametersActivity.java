package com.cyplay.atproj.asperteam.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.ui.fragment.base.BasePopupFragment;

import javax.inject.Inject;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 18-Jun-18.
 */

public class ParametersActivity extends BaseMenuActivity {

    @Inject
    UserSettingsUtil userSettings;

    @BindView(R.id.versionText)
    TextView versionText;

    @BindView(R.id.bandAgreementButton)
    ImageView bandAgreementButton;

    @BindView(R.id.callAgreementButton)
    ImageView callAgreementButton;

    @BindView(R.id.chatAgreementButton)
    ImageView chatAgreementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionText.setText(pinfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            ;
        }

        updateButtons();
    }

    private void updateButtons() {
        if (userSettings.isBandUseAgree())
            bandAgreementButton.setImageResource(R.drawable.ic_switch_on);
        else
            bandAgreementButton.setImageResource(R.drawable.ic_switch_off);

        if (userSettings.isCallUseAgree())
            callAgreementButton.setImageResource(R.drawable.ic_switch_on);
        else
            callAgreementButton.setImageResource(R.drawable.ic_switch_off);

        if (userSettings.isChatUseAgree())
            chatAgreementButton.setImageResource(R.drawable.ic_switch_on);
        else
            chatAgreementButton.setImageResource(R.drawable.ic_switch_off);
    }

    @OnClick(R.id.bandAgreementButton)
    public void onClickBandAgreementButton() {
        getPopup().initPopup(RequestCode.BAND_AGREEMENT_REQUEST,
                getString(R.string.band_permission_title),
                getString(R.string.band_permission_description));
        getPopup().setPositive(getString(R.string.yes));
        getPopup().setNegative(getString(R.string.no));
        getPopup().show();
    }

    @OnClick(R.id.callAgreementButton)
    public void onClickCallAgreementButton() {
        getPopup().initPopup(RequestCode.CALL_AGREEMENT_REQUEST,
                getString(R.string.call_permission_title),
                getString(R.string.call_permission_description));
        getPopup().setPositive(getString(R.string.yes));
        getPopup().setNegative(getString(R.string.no));
        getPopup().show();
    }

    @OnClick(R.id.chatAgreementButton)
    public void onClickChatAgreementButton() {
        getPopup().initPopup(RequestCode.CHAT_AGREEMENT_REQUEST,
                getString(R.string.chat_permission_title),
                getString(R.string.chat_permission_description));
        getPopup().setPositive(getString(R.string.yes));
        getPopup().setNegative(getString(R.string.no));
        getPopup().show();
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.BAND_AGREEMENT_REQUEST:
                userSettings.setBandUseAgree(!userSettings.isBandUseAgree());
                break;
            case RequestCode.CALL_AGREEMENT_REQUEST:
                userSettings.setCallUseAgree(!userSettings.isCallUseAgree());
                break;
            case RequestCode.CHAT_AGREEMENT_REQUEST:
                userSettings.setChatUseAgree(!userSettings.isChatUseAgree());
                break;

        }

        updateButtons();
    }
}
