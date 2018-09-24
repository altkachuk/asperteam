package com.cyplay.atproj.asperteam.ui.fragment;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.DashboardActivity;
import com.cyplay.atproj.asperteam.ui.activity.HelpActivity;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProblemCategoriesActivity;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

import atproj.cyplay.com.asperteamapi.util.FacebookMessangerUtils;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 19-Apr-18.
 */

public class BottomMenuFragment extends BaseFragment {

    @Inject
    UserSettingsUtil userSettings;

    @BindView(R.id.homeItem)
    ImageView homeItem;

    @BindView(R.id.qualifySituationsItem)
    ImageView qualifySituationsItem;

    @BindView(R.id.callItem)
    ImageView callItem;

    @BindView(R.id.dashboardItem)
    ImageView dashboardItem;

    private Class _activeActivityClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false);
    }

    public void setActiveActivityClass(Class activityClass) {
        _activeActivityClass = activityClass;


        if (_activeActivityClass.equals(HomeActivity.class)) {
            homeItem.setImageResource(R.drawable.ic_home_activ);
            homeItem.setEnabled(false);
        } else {
            homeItem.setImageResource(R.drawable.ic_home);
            homeItem.setEnabled(true);
        }

        if (_activeActivityClass.equals(ProblemCategoriesActivity.class)) {
            qualifySituationsItem.setImageResource(R.drawable.ic_info_activ);
            qualifySituationsItem.setEnabled(false);
        } else {
            qualifySituationsItem.setImageResource(R.drawable.ic_info);
            qualifySituationsItem.setEnabled(true);
        }

        if (_activeActivityClass.equals(HelpActivity.class)) {
            callItem.setImageResource(R.drawable.ic_phone_activ);
            callItem.setEnabled(false);
        } else {
            callItem.setImageResource(R.drawable.ic_phone);
            callItem.setEnabled(true);
        }

        if (_activeActivityClass.equals(DashboardActivity.class)) {
            dashboardItem.setImageResource(R.drawable.ic_dashboard_active);
            dashboardItem.setEnabled(false);
        } else {
            dashboardItem.setImageResource(R.drawable.ic_dashboard);
            dashboardItem.setEnabled(true);
        }
    }

    @OnClick(R.id.homeItem)
    public void onClickHomeItem() {
        Intent homeIntent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        this.startActivity(homeIntent);
        getActivity().finish();
    }

    @OnClick(R.id.qualifySituationsItem)
    public void onClickQualifySituationaItem() {
        Intent problemCategoriesIntent = new Intent(getActivity().getApplicationContext(), ProblemCategoriesActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProblemCategoriesActivity.class)
                .addNextIntent(problemCategoriesIntent)
                .startActivities();
    }

    @OnClick(R.id.callItem)
    public void onClickCallItem() {
        Intent helpIntent = new Intent(getActivity().getApplicationContext(), HelpActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(HelpActivity.class)
                .addNextIntent(helpIntent)
                .startActivities();
    }

    @OnClick(R.id.dashboardItem)
    public void onClickDashboardItem() {
        Intent dashboardIntent = new Intent(getActivity().getApplicationContext(), DashboardActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(DashboardActivity.class)
                .addNextIntent(dashboardIntent)
                .startActivities();
    }

    @OnClick(R.id.communityItem)
    public void onClickCommunityItem() {
        openMessageDialog(getString(R.string.community_fb_url));
    }

    private void openMessageDialog(String fbId) {
        if (!userSettings.isChatUseAgree()) {
            getPopup().initPopup(RequestCode.FB_PERMISSION_DENIED_REQUEST,
                    "Permission Denied",
                    "Chat Permission Denied");
            getPopup().setPositive(getString(R.string.ok_button));
            getPopup().show();
        } else if (FacebookMessangerUtils.isInstalled(getActivity().getApplicationContext())) {
            FacebookMessangerUtils.startMessanger(getActivity(), fbId);
        } else {
            getPopup().initPopup(0,
                    getString(R.string.messanger_not_installed_title),
                    getString(R.string.messanger_not_installed_description));
            getPopup().setPositive(getString(R.string.agree_button));
            getPopup().show();
        }
    }

}
