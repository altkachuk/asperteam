package com.cyplay.atproj.asperteam.ui.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseActivity;
import com.cyplay.atproj.asperteam.ui.fragment.LoginFragment;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

public class StartActivity extends BaseActivity implements LoginFragment.OnLoginListener {

    private final int TERMS_OF_CONDITION = 0;
    private final int BAND_AGREEMENT = 1;
    private final int CALL_AGREEMENT = 2;
    private final int CHAT_AGREEMENT = 3;

    @Inject
    AsperTeamApi asperTeamApi;

    @Inject
    UserSettingsUtil userSettings;

    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginFragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.loginFragment);
    }

    @Override
    public void onLogin() {
        Spanned description = Html.fromHtml(
                getString(R.string.terms_of_condition_text1) +
                        getString(R.string.terms_of_condition_text2) +
                        getString(R.string.terms_of_condition_text3)
        );
        getPopup().initPopup(
                TERMS_OF_CONDITION,
                getString(R.string.terms_of_condition_title),
                description);
        getPopup().setImage(R.drawable.img_braccelet);
        getPopup().setPositive(getString(R.string.agree_button));
        getPopup().setNegative(getString(R.string.disagree_button));
        getPopup().show();
    }

    @Override
    protected void onPopupNegativeClick(int requestCode) {
        if (requestCode == TERMS_OF_CONDITION) {
            loginFragment.clear();
        } else {
            setAgreementSettings(requestCode, false);
            updateAgreement(requestCode);
        }
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        setAgreementSettings(requestCode, true);
        updateAgreement(requestCode);
    }

    private void setAgreementSettings(int requestCode, boolean isAgree) {
        switch (requestCode) {
            case BAND_AGREEMENT:
                userSettings.setBandUseAgree(isAgree);
                break;
            case CALL_AGREEMENT:
                userSettings.setCallUseAgree(isAgree);
                break;
            case CHAT_AGREEMENT:
                userSettings.setChatUseAgree(isAgree);
                break;
        }
    }

    private void updateAgreement(int requestCode) {
        switch (requestCode) {
            case TERMS_OF_CONDITION:
                getPopup().initPopup(
                        BAND_AGREEMENT,
                        getString(R.string.band_agreement_title),
                        getString(R.string.band_agreement_text));
                getPopup().setImage(R.drawable.img_braccelet);
                getPopup().setPositive(getString(R.string.agree_button));
                getPopup().setNegative(getString(R.string.disagree_button));
                getPopup().show();
                break;
            case BAND_AGREEMENT:
                getPopup().initPopup(
                        CALL_AGREEMENT,
                        getString(R.string.use_call_agreement_title),
                        getString(R.string.call_agreement_text));
                getPopup().setPositive(getString(R.string.agree_button));
                getPopup().setNegative(getString(R.string.disagree_button));
                getPopup().show();
                break;
            case CALL_AGREEMENT:
                getPopup().initPopup(
                        CHAT_AGREEMENT,
                        getString(R.string.use_chat_agreement_title),
                        getString(R.string.chat_agreement_text));
                getPopup().setPositive(getString(R.string.agree_button));
                getPopup().setNegative(getString(R.string.disagree_button));
                getPopup().show();
                break;
            case CHAT_AGREEMENT:
                if (userSettings.isBandUseAgree())
                    startBandActivity();
                else
                    startProfileActivity();
                break;
        }
    }

    private void startBandActivity() {
        Intent bandIntent = new Intent(this.getApplicationContext(), BandActivity.class);
        this.startActivity(bandIntent);
        this.finish();
    }

    private void startProfileActivity() {
        Intent profileIntent = new Intent(this.getApplicationContext(), ProfileActivity.class);
        TaskStackBuilder.create(this)
                .addParentStack(MySuggestionsActivity.class)
                .addNextIntent(profileIntent)
                .startActivities();
    }
}
