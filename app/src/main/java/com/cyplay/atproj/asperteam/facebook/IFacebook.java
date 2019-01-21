package com.cyplay.atproj.asperteam.facebook;

import android.app.Activity;
import android.content.Intent;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

/**
 * Created by andre on 21-Jan-19.
 */

public interface IFacebook {

    boolean loggedIn();
    void login(Activity activity, FacebookCallback<LoginResult> callback);
    void getProfile(final FacebookManager.MeRequestCallback callback);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
