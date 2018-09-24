package com.cyplay.atproj.asperteam.ui.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.RoleType;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuResourceActivity;
import atproj.cyplay.com.asperteamapi.util.FacebookMessangerUtils;
import atproj.cyplay.com.asperteamapi.util.PhoneUtils;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 02-May-18.
 */

public class HelpActivity extends BaseMenuResourceActivity {

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    Picasso picasso;

    @BindView(R.id.photoCoachImage)
    ImageView photoCoachImage;


    private User _coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    protected void onPostMenuCreated() {
        getStaff(_user.getStaffId(RoleType.COACH));
    }

    private void getStaff(String id) {
        showPreloader();
        profileInteractor.getStaff(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                if (user.getRole() == RoleType.COACH) {
                    _coach = user;
                    if (user.getImage() != null && user.getImage().length() > 0)
                        picasso.load(user.getImage()).transform(new CircleTransform(getApplicationContext(), R.color.colorImageCircleStroke)).into(photoCoachImage);
                }
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @OnClick(R.id.relaxItem)
    public void onClickRelaxItem() {
        Intent relaxIntent = new Intent(getApplicationContext(), RelaxActivity.class);
        TaskStackBuilder.create(this)
                .addParentStack(RelaxActivity.class)
                .addNextIntent(relaxIntent)
                .startActivities();
    }

    @OnClick(R.id.qualifySituationsItem)
    public void onClickQualifySituationaItem() {
        Intent problemCategoriesIntent = new Intent(getApplicationContext(), ProblemCategoriesActivity.class);
        TaskStackBuilder.create(this)
                .addParentStack(ProblemCategoriesActivity.class)
                .addNextIntent(problemCategoriesIntent)
                .startActivities();
    }

    @OnClick(R.id.callCoachButton)
    public void onClickCallCoachButton() {
        startDialActivity(_coach.getPhone());
    }

    @OnClick(R.id.chatCoachButton)
    public void onClickChatCoachButton() {
        openMessageDialog(_coach.getFbId());
    }

    private void openMessageDialog(String fbId) {
        if (!userSettings.isChatUseAgree()) {
            getPopup().initPopup(RequestCode.FB_PERMISSION_DENIED_REQUEST,
                    "Permission Denied",
                    "Chat Permission Denied");
            getPopup().setPositive(getString(R.string.ok_button));
            getPopup().show();
        } else if (!FacebookMessangerUtils.isInstalled(getApplicationContext())) {
            getPopup().initPopup(0,
                    getString(R.string.messanger_not_installed_title),
                    getString(R.string.messanger_not_installed_description));
            getPopup().setPositive(getString(R.string.agree_button));
            getPopup().show();
        } else if (_coach.getFbId() == null) {
            getPopup().initPopup(0,
                    getString(R.string.fb_account_empty_title),
                    getString(R.string.fb_account_empty_description));
            getPopup().setPositive(getString(R.string.agree_button));
            getPopup().show();
        } else {
            FacebookMessangerUtils.startMessanger(this, fbId);
        }
    }

    private void startDialActivity(final String phoneNumber) {
        if (!userSettings.isCallUseAgree()) {
            getPopup().initPopup(RequestCode.CALL_PERMISSION_DENIED_REQUEST,
                    "Permission Denied",
                    "Call Permission Denied");
            getPopup().setPositive(getString(R.string.ok_button));
            getPopup().show();
        } else if (phoneNumber == null) {
            getPopup().initPopup(0, "Phone number is not valid");
            getPopup().setPositive("OK");
            getPopup().show();
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", PhoneUtils.validate(phoneNumber), null)));
        }
    }

}
