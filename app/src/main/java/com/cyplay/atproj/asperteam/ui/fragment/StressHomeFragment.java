package com.cyplay.atproj.asperteam.ui.fragment;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.RoleType;
import atproj.cyplay.com.asperteamapi.model.Stress;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import com.cyplay.atproj.asperteam.ui.activity.ProblemCategoriesActivity;
import com.cyplay.atproj.asperteam.ui.customview.StressScaleView;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;
import com.cyplay.atproj.asperteam.utils.LocationManager;
import com.cyplay.atproj.asperteam.utils.MailgunSender;
import com.cyplay.atproj.asperteam.utils.MsBandManager;
import com.cyplay.atproj.asperteam.utils.NotificationSender;

import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 17-Apr-18.
 */

public class StressHomeFragment extends BaseResourceFragment {

    @Inject
    MsBandManager bandManager;

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    ProfileAdminInteractor profileAdminInteractor;

    @Inject
    StressInteractor stressInteractor;

    @Inject
    MailgunSender mailgunSender;

    @Inject
    NotificationSender notificationSender;

    @BindView(R.id.stressScaleView)
    StressScaleView stressScaleView;

    @BindView(R.id.stressPopupView)
    RelativeLayout stressPopupView;

    private String _coachFirebaseToken;
    private User _patient;
    private User _coach;
    private float _stressLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stress_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stressScaleView.setMinimum(0);
        stressScaleView.setMaximum(100);

        stressPopupView.setVisibility(View.GONE);

        getPatient(userSettings.getId());
    }

    private void getPatient(String id) {
        showPreloader();

        profileInteractor.getPatient(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _patient = user;
                userSettings.setStressLevelMin(_patient.getPatient().getStressLevelMin());
                userSettings.setStressLevelMax(_patient.getPatient().getStressLevelMax());
                getCoachOrigin(user.getCoachOrigin());
                getStaff(user.getStaffId(RoleType.COACH));
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void getCoachOrigin(String id) {
        showPreloader();

        profileAdminInteractor.getUser(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _coachFirebaseToken = user.getFirebaseToken();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void getStaff(String id) {
        showPreloader();
        profileInteractor.getStaff(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _coach = user;
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    public void onNewStressLevel(final float stressLevel) {
        stressScaleView.setStress((int)stressLevel);
    }

    public void onStress(float stressLevel) {
        _stressLevel = stressLevel;
        stressPopupView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.yesButton)
    public void onClickYesButton() {
        stressPopupView.setVisibility(View.GONE);

        if (!userSettings.isGeolocationSending()) {
            sendValidateByUserMessage();
        } else if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    1);
        } else {
            sendStress();
            sendValidateByUserMessage();
        }
    }

    @OnClick(R.id.cancelButton)
    public void onClickCancelButton() {
        stressPopupView.setVisibility(View.GONE);

        sendMessage(R.string.subject_stress_not_validated_by_user, R.string.text_stress_not_validated_by_user);
    }

    public void sendStress() {
        LocationManager.getInstance(getActivity().getApplicationContext()).start((int)_stressLevel);
    }

    public void sendValidateByUserMessage() {
        sendMessage(R.string.subject_stress_validated_by_user, R.string.text_stress_validated_by_user);
        openProblemCategoriesActivity();
    }

    private void openProblemCategoriesActivity() {
        Intent problemCategoriesIntent = new Intent(getActivity().getApplicationContext(), ProblemCategoriesActivity.class);
        TaskStackBuilder.create(getActivity())
                .addParentStack(ProblemCategoriesActivity.class)
                .addNextIntent(problemCategoriesIntent)
                .startActivities();
    }

    private void sendMessage(int subjectRes, int textRes) {
        String username = _patient.getFirstName() + " " + _patient.getLastName();
        String from = username + " <" + _patient.getEmail() + ">";
        String subject = getString(subjectRes).replace("_username_", username);
        String text = getString(textRes).replace("_username_", username);

        mailgunSender.run(from, _coach.getEmail(), getString(R.string.coach_email), subject, text, null);
        if (_coachFirebaseToken != null)
            notificationSender.run(_patient.getId(), _coachFirebaseToken, subject, text, "PatientActivity");
    }
}
