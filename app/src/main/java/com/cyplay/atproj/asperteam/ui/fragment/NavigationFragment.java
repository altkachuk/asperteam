package com.cyplay.atproj.asperteam.ui.fragment;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;

import atproj.cyplay.com.asperteamapi.model.User;

import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.DashboardActivity;
import com.cyplay.atproj.asperteam.ui.activity.FaqActivity;
import com.cyplay.atproj.asperteam.ui.activity.HelpActivity;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.cyplay.atproj.asperteam.ui.activity.MySuggestionsActivity;
import com.cyplay.atproj.asperteam.ui.activity.ParametersActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProblemCategoriesActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.TermsOfServiceActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;
import atproj.cyplay.com.asperteamapi.util.FacebookMessangerUtils;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 17-Apr-18.
 */

public class NavigationFragment extends BaseResourceFragment {

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    Picasso picasso;

    @BindView(R.id.photoImage)
    ImageView photoImage;

    @BindView(R.id.nameText)
    TextView nameText;

    @BindView(R.id.homeItem)
    RelativeLayout homeItem;

    @BindView(R.id.ecosysteItem)
    RelativeLayout ecosysteItem;

    @BindView(R.id.resourcesItem)
    RelativeLayout resourcesItem;

    @BindView(R.id.qualifySituationsItem)
    RelativeLayout qualifySituationsItem;

    @BindView(R.id.faqItem)
    RelativeLayout faqItem;

    @BindView(R.id.suggestionsItem)
    RelativeLayout suggestionsItem;

    @BindView(R.id.dashboardItem)
    RelativeLayout dashboardItem;

    @BindView(R.id.parametersItem)
    RelativeLayout parametersItem;

    @BindView(R.id.termsOfServiceItem)
    RelativeLayout termsOfServiceItem;

    private Class _activeActivityClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        update();
        load();
    }

    public void setUser(User user) {
        picasso.load(user.getImage()).transform(new CircleTransform(getActivity().getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);
        nameText.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void setActiveActivityClass(Class activityClass) {
        _activeActivityClass = activityClass;
    }

    protected void update() {
        changeActiveState(homeItem, HomeActivity.class, R.drawable.ic_home_activ, R.drawable.ic_home);
        changeActiveState(ecosysteItem, ProfileActivity.class, R.drawable.ic_info_activ, R.drawable.ic_info);
        changeActiveState(qualifySituationsItem, ProblemCategoriesActivity.class, R.drawable.ic_info_activ, R.drawable.ic_info);
        changeActiveState(resourcesItem, HelpActivity.class, R.drawable.ic_info_activ, R.drawable.ic_info);
        changeActiveState(faqItem, FaqActivity.class, R.drawable.ic_faq_active, R.drawable.ic_faq);
        changeActiveState(suggestionsItem, MySuggestionsActivity.class, R.drawable.ic_tag_active, R.drawable.ic_tag);
        changeActiveState(dashboardItem, DashboardActivity.class, R.drawable.ic_dashboard_active, R.drawable.ic_dashboard);
        changeActiveState(parametersItem, ParametersActivity.class, R.drawable.ic_settings_active, R.drawable.ic_settings);
        changeActiveState(termsOfServiceItem, TermsOfServiceActivity.class, R.drawable.ic_info_activ, R.drawable.ic_info);
    }

    private void changeActiveState(RelativeLayout item, Class activeClass, int activeIcRes, int notActiveIcRes) {
        ImageView itemImageView = (ImageView) item.getChildAt(0);
        TextView itemTextView = (TextView) item.getChildAt(1);

        if (_activeActivityClass.equals(activeClass)) {
            itemImageView.setImageResource(activeIcRes);
            itemTextView.setTextColor(Color.parseColor("#4e7b9f"));
            item.setEnabled(false);
            item.setBackgroundResource(R.drawable.background_menu_selected_item);
        } else {
            itemImageView.setImageResource(notActiveIcRes);
            itemTextView.setTextColor(Color.parseColor("#5a5a5a"));
            item.setEnabled(true);
            item.setBackgroundResource(R.drawable.background_menu_item);
        }
    }

    @OnClick(R.id.photoImage)
    public void onClickProfile() {
        Intent profileIntent = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProfileActivity.class)
                .addNextIntent(profileIntent)
                .startActivities();
    }

    @OnClick(R.id.homeItem)
    public void onClickHomeItem() {
        Intent homeIntent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        this.startActivity(homeIntent);
        getActivity().finish();
    }

    @OnClick(R.id.ecosysteItem)
    public void onClickEcosystemItem() {
        Intent profileIntent = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProfileActivity.class)
                .addNextIntent(profileIntent)
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

    @OnClick(R.id.resourcesItem)
    public void onClickResourceItem() {
        Intent helpIntent = new Intent(getActivity().getApplicationContext(), HelpActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(HelpActivity.class)
                .addNextIntent(helpIntent)
                .startActivities();
    }

    @OnClick(R.id.qualifySituationsItem)
    public void onClickQualifySituationaItem() {
        Intent problemCategoriesIntent = new Intent(getActivity().getApplicationContext(), ProblemCategoriesActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProblemCategoriesActivity.class)
                .addNextIntent(problemCategoriesIntent)
                .startActivities();
    }

    @OnClick(R.id.communityItem)
    public void onClickCommunityItem() {
        openMessageDialog(getString(R.string.community_fb_url));
    }


    @OnClick(R.id.suggestionsItem)
    public void onClickSuggestionsItem() {
        Intent sugsestionsIntent = new Intent(getActivity().getApplicationContext(), MySuggestionsActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(MySuggestionsActivity.class)
                .addNextIntent(sugsestionsIntent)
                .startActivities();
    }

    @OnClick(R.id.faqItem)
    public void onClickFaqItem() {
        Intent faqIntent = new Intent(getActivity().getApplicationContext(), FaqActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(FaqActivity.class)
                .addNextIntent(faqIntent)
                .startActivities();
    }

    @OnClick(R.id.parametersItem)
    public void onClickParametersItem() {
        Intent parametersIntent = new Intent(getActivity().getApplicationContext(), ParametersActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ParametersActivity.class)
                .addNextIntent(parametersIntent)
                .startActivities();
    }

    @OnClick(R.id.termsOfServiceItem)
    public void onClickTermsOfServicetem() {
        Intent termsOfServiceIntent = new Intent(getActivity().getApplicationContext(), TermsOfServiceActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(TermsOfServiceActivity.class)
                .addNextIntent(termsOfServiceIntent)
                .startActivities();
    }

    @OnClick(R.id.signoutItem)
    public void onClickSignoutItem() {
        getActivity().finishAndRemoveTask();
    }

    @OnClick(R.id.deleteAccountItem)
    public void onDeleteAccountItem() {
        ((BaseMenuActivity)getActivity()).closeMenu();
        getPopup().initPopup(
                RequestCode.DELETE_ACCOUNT_REQUEST,
                getString(R.string.delete_account_title));
        getPopup().setPositive(getString(R.string.yes));
        getPopup().setNegative(getString(R.string.no));
        getPopup().show();
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
