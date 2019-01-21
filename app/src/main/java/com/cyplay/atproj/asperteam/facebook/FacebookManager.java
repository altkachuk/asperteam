package com.cyplay.atproj.asperteam.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import atproj.cyplay.com.asperteamapi.model.FacebookProfile;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

/**
 * Created by andre on 07-Apr-18.
 */

public class FacebookManager implements IFacebook {

    private Gson _gson;
    private CallbackManager _callbackManager;
    private LoginFacebookProperties _properties = new LoginFacebookProperties();
    private FacebookCallback<LoginResult> _loginCallback;

    public CallbackManager getCallbackManager() {
        return _callbackManager;
    }



    /**
     * Gets the default audience to use when the user logs in.
     * This value is only useful when specifying publish permissions for the native
     * login dialog.
     *
     * @return the default audience value to use
     */
    public DefaultAudience getDefaultAudience() {
        return _properties.getDefaultAudience();
    }

    public FacebookManager(Gson gson) {
        _gson = gson;
        _callbackManager = CallbackManager.Factory.create();
    }

    //-----------------------------------------------------
    // IFacebook

    @Override
    public boolean loggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    @Override
    public void login(Activity activity, FacebookCallback<LoginResult> callback) {
        _loginCallback = callback;

        final LoginManager loginManager = getLoginManager();
        loginManager.registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                _loginCallback.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                _loginCallback.onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                _loginCallback.onError(error);
            }
        });

        if (LoginAuthorizationType.PUBLISH.equals(_properties.authorizationType)) {
            loginManager.logInWithPublishPermissions(activity, _properties.permissions);
        } else {
            loginManager.logInWithReadPermissions(activity, _properties.permissions);
        }
    }

    @Override
    public void getProfile(final MeRequestCallback callback) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                (object, response) -> {
                        FacebookProfile profile = _gson.fromJson(object.toString(), FacebookProfile.class);
                        callback.onCompleted(profile);
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,link,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------------
    // private methods

    private LoginManager getLoginManager() {
        LoginManager manager = LoginManager.getInstance();
        manager.setDefaultAudience(getDefaultAudience());
        manager.setLoginBehavior(getLoginBehavior());
        return manager;
    }

    /**
     * Gets the login behavior during authorization. If null is returned, the default
     * ({@link com.facebook.login.LoginBehavior LoginBehavior.NATIVE_WITH_FALLBACK}
     * will be used.
     *
     * @return loginBehavior The {@link com.facebook.login.LoginBehavior LoginBehavior} that
     * specifies what behaviors should be attempted during
     * authorization.
     */
    private LoginBehavior getLoginBehavior() {
        return _properties.getLoginBehavior();
    }

    //-----------------------------------------------------
    // LoginFacebookProperties

    static class LoginFacebookProperties {
        private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
        private List<String> permissions = Collections.emptyList();
        private LoginAuthorizationType authorizationType = null;
        private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;

        public void setDefaultAudience(DefaultAudience defaultAudience) {
            this.defaultAudience = defaultAudience;
        }

        public DefaultAudience getDefaultAudience() {
            return defaultAudience;
        }

        public void setReadPermissions(List<String> permissions) {

            if (LoginAuthorizationType.PUBLISH.equals(authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after " +
                        "setPublishPermissions has been called.");
            }
            this.permissions = permissions;
            authorizationType = LoginAuthorizationType.READ;
        }

        public void setPublishPermissions(List<String> permissions) {

            if (LoginAuthorizationType.READ.equals(authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after " +
                        "setReadPermissions has been called.");
            }
            if (Utility.isNullOrEmpty(permissions)) {
                throw new IllegalArgumentException(
                        "Permissions for publish actions cannot be null or empty.");
            }
            this.permissions = permissions;
            authorizationType = LoginAuthorizationType.PUBLISH;
        }

        List<String> getPermissions() {
            return permissions;
        }

        public void clearPermissions() {
            permissions = null;
            authorizationType = null;
        }

        public void setLoginBehavior(LoginBehavior loginBehavior) {
            this.loginBehavior = loginBehavior;
        }

        public LoginBehavior getLoginBehavior() {
            return loginBehavior;
        }
    }

    public interface MeRequestCallback {
        void onCompleted(FacebookProfile profile);
    }
}
