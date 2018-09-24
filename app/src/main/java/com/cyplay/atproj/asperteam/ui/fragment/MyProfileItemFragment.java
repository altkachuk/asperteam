package com.cyplay.atproj.asperteam.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;

import atproj.cyplay.com.asperteamapi.model.User;

import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.MyProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseResourceActivity;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;
import com.cyplay.atproj.asperteam.utils.FacebookManager;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 05-Apr-18.
 */

public class MyProfileItemFragment extends BaseResourceFragment {

    @Inject
    Picasso picasso;

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    FacebookManager facebook;

    @BindView(R.id.photoImage)
    ImageView photoImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_my_profile, container, false);
    }

    public void setUser(User user) {
        picasso.load(user.getImage()).transform(new CircleTransform(getActivity().getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);
    }

    @OnClick(R.id.photoImage)
    protected void onPhotoImageClick() {
        if (facebook.loggedIn()) {
            openMyProfileActivity();
        } else {
            getPopup().initPopup(ProfileActivity.ADD_FACEBOOK_ACCOUNT,
                    getString(R.string.facebook_popup_title));
            getPopup().setImage(R.drawable.img_facebook);
            getPopup().setPositive(getString(R.string.agree_button));
            getPopup().setNegative(getString(R.string.disagree_button));
            getPopup().show();
        }
    }

    private void openMyProfileActivity() {
        Intent myProfileIntent = new Intent(getActivity().getApplicationContext(), MyProfileActivity.class);
        getActivity().startActivityForResult(myProfileIntent, RequestCode.RELOAD_REQUEST);
    }
}
