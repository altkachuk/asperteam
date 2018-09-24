package com.cyplay.atproj.asperteam.utils;

import android.app.Activity;
import android.os.Bundle;

import atproj.cyplay.com.asperteamapi.model.FacebookProfile;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by andre on 07-Apr-18.
 */

public class FacebookManager {

    private Gson _gson;
    private CallbackManager _callbackManager;
    private LoginFacebookProperties _properties = new LoginFacebookProperties();
    private FacebookCallback<LoginResult> _loginCallback;
    private AccessToken _accessToken;

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

    /**
     * Gets the login behavior during authorization. If null is returned, the default
     * ({@link com.facebook.login.LoginBehavior LoginBehavior.NATIVE_WITH_FALLBACK}
     * will be used.
     *
     * @return loginBehavior The {@link com.facebook.login.LoginBehavior LoginBehavior} that
     * specifies what behaviors should be attempted during
     * authorization.
     */
    public LoginBehavior getLoginBehavior() {
        return _properties.getLoginBehavior();
    }

    public FacebookManager(Gson gson) {
        _gson = gson;
        _callbackManager = CallbackManager.Factory.create();
        _accessToken = AccessToken.getCurrentAccessToken();
    }

    /**
     *
     * @return
     */
    public boolean loggedIn() {
        return _accessToken != null;
    }

    /**
     *
     * @param callback
     */
    public void login(Activity activity, FacebookCallback<LoginResult> callback) {
        _loginCallback = callback;

        final LoginManager loginManager = getLoginManager();
        loginManager.registerCallback(_callbackManager, loginResultFacebookCallback);

        if (LoginAuthorizationType.PUBLISH.equals(_properties.authorizationType)) {
            loginManager.logInWithPublishPermissions(activity, _properties.permissions);
        } else {
            loginManager.logInWithReadPermissions(activity, _properties.permissions);
        }
    }

    /**
     *
     * @param callback a callback that will be called when the request is completed to handle
     *                    success or error conditions
     */
    public void meRequest(final MeRequestCallback callback) {
        GraphRequest request = GraphRequest.newMeRequest(_accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FacebookProfile profile = _gson.fromJson(object.toString(), FacebookProfile.class);
                        callback.onCompleted(profile);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,link,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected FacebookCallback<LoginResult> loginResultFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            _accessToken = loginResult.getAccessToken();
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
    };

    protected LoginManager getLoginManager() {
        LoginManager manager = LoginManager.getInstance();
        manager.setDefaultAudience(getDefaultAudience());
        manager.setLoginBehavior(getLoginBehavior());
        return manager;
    }

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
