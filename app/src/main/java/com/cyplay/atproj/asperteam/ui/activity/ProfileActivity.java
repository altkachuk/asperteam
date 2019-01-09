package com.cyplay.atproj.asperteam.ui.activity;

import android.content.Intent;
import android.os.Bundle;


import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.FacebookProfile;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuResourceActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseResourceActivity;
import com.cyplay.atproj.asperteam.ui.fragment.MyProfileItemFragment;
import com.cyplay.atproj.asperteam.ui.fragment.StaffProfileItemFragment;
import com.cyplay.atproj.asperteam.utils.FacebookManager;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


import javax.inject.Inject;

/**
 * Created by andre on 05-Apr-18.
 */

public class ProfileActivity extends BaseMenuResourceActivity {

    public static final int ADD_FACEBOOK_ACCOUNT = 0;

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    FacebookManager facebook;

    MyProfileItemFragment myProfile;
    StaffProfileItemFragment coachProfile;
    StaffProfileItemFragment permanentCoachProfile;
    StaffProfileItemFragment profileManagerProfile;
    StaffProfileItemFragment hrManagerProfile;
    StaffProfileItemFragment tutorProfile;
    StaffProfileItemFragment disabilityManagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myProfile = (MyProfileItemFragment) getFragmentManager().findFragmentById(R.id.myProfile);
        coachProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.coachProfile);
        permanentCoachProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.permanentCoachProfile);
        profileManagerProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.profileManagerProfile);
        hrManagerProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.hrManagerProfile);
        tutorProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.tutorProfile);
        disabilityManagerProfile = (StaffProfileItemFragment) getFragmentManager().findFragmentById(R.id.disabilityManagerProfile);
    }

    @Override
    protected void onPostMenuCreated() {
        myProfile.setUser(_user);
        coachProfile.setUser(_user);
        permanentCoachProfile.setUser(_user);
        profileManagerProfile.setUser(_user);
        hrManagerProfile.setUser(_user);
        tutorProfile.setUser(_user);
        disabilityManagerProfile.setUser(_user);
    }

    @Override
    protected void onPopupNegativeClick(int requestCode) {
        switch (requestCode) {
            case ADD_FACEBOOK_ACCOUNT:
                break;
        }
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        super.onPopupPositiveClick(requestCode);
        switch (requestCode) {
            case ADD_FACEBOOK_ACCOUNT:
                loginFB();
                break;
        }
    }

    private void loginFB() {
        facebook.login(this, loginResultFacebookCallback);
    }

    protected FacebookCallback<LoginResult> loginResultFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            showPreloader();
            facebook.meRequest(meRequestCallback);
        }

        @Override
        public void onCancel() {
            hidePreloader();
        }

        @Override
        public void onError(FacebookException error) {
            hidePreloader();
        }
    };

    protected FacebookManager.MeRequestCallback meRequestCallback = new FacebookManager.MeRequestCallback() {
        @Override
        public void onCompleted(FacebookProfile profile) {
            hidePreloader();
            updateMyProfileByFacebook(profile);
        }
    };

    private void updateMyProfileByFacebook(FacebookProfile profile) {
        showPreloader();
        User user = new User();
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        //user.setImage(profile.getPictureUrl());
        user.setEmail(profile.getEmail());
        user.setFbAccount(profile.getLink());

        profileInteractor.updateUser(userSettings.getId(), userSettings.getUsername(), user, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebook.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openMyProfileActivity() {
        Intent myProfileIntent = new Intent(this.getApplicationContext(), MyProfileActivity.class);
        this.startActivityForResult(myProfileIntent, RequestCode.RELOAD_REQUEST);
    }
}
