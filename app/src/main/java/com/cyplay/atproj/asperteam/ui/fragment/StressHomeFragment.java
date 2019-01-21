package com.cyplay.atproj.asperteam.ui.fragment;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cyplay.atproj.asperteam.R;

import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;

import com.cyplay.atproj.asperteam.presenters.StressPresenter;
import com.cyplay.atproj.asperteam.ui.activity.ProblemCategoriesActivity;
import com.cyplay.atproj.asperteam.ui.customview.StressScaleView;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;
import com.cyplay.atproj.asperteam.utils.NotificationSender;
import com.cyplay.atproj.asperteam.views.StressView;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 17-Apr-18.
 */

public class StressHomeFragment extends BaseResourceFragment implements StressView {

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    ProfileAdminInteractor profileAdminInteractor;

    @Inject
    StressInteractor stressInteractor;

    @BindView(R.id.stressScaleView)
    StressScaleView stressScaleView;

    @BindView(R.id.stressPopupView)
    RelativeLayout stressPopupView;

    private StressPresenter _stressPresenter;
    private MediaPlayer _mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stress_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stressScaleView.setMinimum(0);
        stressScaleView.setMaximum(100);
        hidePopup();

        _mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        _stressPresenter = new StressPresenter(getActivity().getApplicationContext(),
                this, userSettings, profileInteractor, profileAdminInteractor);
    }

    public void onNewStressLevel(final int stressLevel) {
        stressScaleView.setStress(stressLevel);
    }

    public void onStress(int stressLevel, int rri) {
        _stressPresenter.setStress(stressLevel, rri);
    }

    public void sendStress() {
        _stressPresenter.confirmStress();
    }

    //------------------------------------------
    // Listeners

    @OnClick(R.id.yesButton)
    public void onClickYesButton() {
        _stressPresenter.confirmStress();
    }

    @OnClick(R.id.cancelButton)
    public void onClickCancelButton() {
        _stressPresenter.cancel();
    }

    //------------------------------------------
    // StressView

    @Override
    public void showPopup() {
        stressPopupView.setVisibility(View.VISIBLE);
        _mediaPlayer.start();
    }

    @Override
    public void hidePopup() {
        stressPopupView.setVisibility(View.GONE);
    }

    @Override
    public boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermisions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[] {
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                },
                1);
    }

    @Override
    public void openProblemCategoriesActivity() {
        Intent problemCategoriesIntent = new Intent(getActivity().getApplicationContext(), ProblemCategoriesActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProblemCategoriesActivity.class)
                .addNextIntent(problemCategoriesIntent)
                .startActivities();
    }
}
