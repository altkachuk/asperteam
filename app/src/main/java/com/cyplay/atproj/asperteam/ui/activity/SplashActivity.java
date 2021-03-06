package com.cyplay.atproj.asperteam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import atproj.cyplay.com.asperteamapi.domain.interactor.LoginInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Login;
import atproj.cyplay.com.asperteamapi.model.OAuth2Credentials;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseActivity;
import com.cyplay.atproj.asperteam.utils.ApplicationUtil;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

/**
 * Created by andre on 30-Mar-18.
 */

public class SplashActivity extends BaseActivity {

    /*@Inject
    AsperTeamApi asperTeamApi;*/

    @Inject
    LoginInteractor loginInteractor;

    @Inject
    UserSettingsUtil userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (userSettings.getToken() != null && userSettings.getId() != null) {
            startHomeActivity();
        } else if (userSettings.getUsername() != null && userSettings.getPassword() != null) {
            doLogin(userSettings.getUsername(), userSettings.getPassword());
        } else {
            startStartActivity();
        }

        /*Intent intent = new Intent(this.getApplicationContext(), ProfileActivity.class);
        this.startFinishActivity(intent);*/
    }

    private void doLogin(String username, String password) {
        Login login = new Login(username, password);
        final SplashActivity activity = this;
        loginInteractor.authenticate(login, new ResourceRequestCallback<OAuth2Credentials>() {
            @Override
            public void onSucess(OAuth2Credentials oAuth2Credentials) {
                userSettings.setAuthCredentials(oAuth2Credentials);
                startHomeActivity();
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    private void startStartActivity() {
        ApplicationUtil.startActivityFinishWithIgnoreBatterOptimization(this, StartActivity.class);
    }

    private void startHomeActivity() {
        ApplicationUtil.startActivityFinishWithIgnoreBatterOptimization(this, HomeActivity.class);
    }


    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AsperTeam", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("AsperTeam", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("AsperTeam", "printHashKey()", e);
        }
    }
}
