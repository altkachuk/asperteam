package com.cyplay.atproj.asperteam.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.fragment.NavigationFragment;
import atproj.cyplay.com.asperteamapi.util.ActivityHelper;
import com.cyplay.atproj.asperteam.utils.MailgunSender;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by andre on 17-Apr-18.
 */

public class BaseMenuActivity extends BaseBandActivity {

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    UserSettingsUtil userSettings;

    // Navigation drawer:
    @Nullable
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    NavigationFragment navigationFragment;

    protected User _user;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationFragment = new NavigationFragment();
        getFragmentManager().beginTransaction().replace(R.id.drawer_layout, navigationFragment).commit();
        navigationFragment.setActiveActivityClass(this.getClass());

        getUser();
    }

    private void getUser() {
        showPreloader();
        profileInteractor.getPatient(userSettings.getId(), new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _user = user;
                userSettings.setStressLevelMin(_user.getPatient().getStressLevelMin());
                userSettings.setStressLevelMax(_user.getPatient().getStressLevelMax());
                navigationFragment.setUser(_user);
                onPostMenuCreated();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    protected void onPostMenuCreated() {
        ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityHelper.hideKeyboard(this);
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END))
            drawerLayout.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();
    }

    public void closeMenu() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END))
            drawerLayout.closeDrawer(GravityCompat.END);
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.DELETE_ACCOUNT_REQUEST:
                sendMessage(getString(R.string.situation_email), getString(R.string.coach_email), R.string.subject_delete_account, R.string.text_delete_account);
                break;
        }
    }

    private void sendMessage(String to, String cc, int subjectRes, int textRes) {
        String username = _user.getFirstName() + " " + _user.getLastName();
        String from = username + " <" + _user.getEmail() + ">";
        String subject = getString(subjectRes).replace("_username_", username);
        String text = getString(textRes).replace("_username_", username);

        MailgunSender mailgunSender = new MailgunSender();
        mailgunSender.run(from, to, subject, text, new MailgunSender.OnMailgunListener() {
            @Override
            public void onSend() {
                hidePreloader();
            }
        });
    }
}
