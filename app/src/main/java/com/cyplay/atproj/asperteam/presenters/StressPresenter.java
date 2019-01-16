package com.cyplay.atproj.asperteam.presenters;

import android.content.Context;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.utils.LocationManager;
import com.cyplay.atproj.asperteam.utils.MailgunSender;
import com.cyplay.atproj.asperteam.utils.NotificationSender;
import com.cyplay.atproj.asperteam.views.StressView;

import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.RoleType;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

/**
 * Created by andre on 10-Jan-19.
 */

public class StressPresenter {

    private Context _context;
    private StressView _stressView;
    private UserSettingsUtil _userSettings;
    private ProfileInteractor _profileInteractor;
    private ProfileAdminInteractor _profileAdminInteractor;
    private NotificationSender _notificationSender;

    private User _patient;
    private User _coach;
    private String _coachFirebaseToken;
    private int _stressLevel;
    private int _rri;

    public StressPresenter(Context context, StressView stressView, UserSettingsUtil userSettings, ProfileInteractor profileInteractor, ProfileAdminInteractor profileAdminInteractor, NotificationSender notificationSender) {
        _context = context;
        _stressView = stressView;
        _userSettings = userSettings;
        _profileInteractor = profileInteractor;
        _profileAdminInteractor = profileAdminInteractor;
        _notificationSender = notificationSender;

        getPatient(userSettings.getId());
    }

    private void getPatient(String id) {
        _stressView.showPreloader();

        _profileInteractor.getPatient(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                _stressView.hidePreloader();
                _patient = user;

                _userSettings.setStressLevelMin(_patient.getPatient().getStressLevelMin());
                _userSettings.setStressLevelMax(_patient.getPatient().getStressLevelMax());

                getCoachOrigin(user.getCoachOrigin());
                getStaff(user.getStaffId(RoleType.COACH));
            }

            @Override
            public void onError(BaseException e) {
                _stressView.hidePreloader();
            }
        });
    }

    private void getCoachOrigin(String id) {
        _stressView.showPreloader();

        _profileAdminInteractor.getUser(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                _stressView.hidePreloader();
                _coachFirebaseToken = user.getFirebaseToken();
            }

            @Override
            public void onError(BaseException e) {
                _stressView.hidePreloader();
            }
        });
    }

    private void getStaff(String id) {
        _stressView.showPreloader();
        _profileInteractor.getStaff(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                _stressView.hidePreloader();
                _coach = user;
            }

            @Override
            public void onError(BaseException e) {
                _stressView.hidePreloader();
            }
        });
    }

    public void setStress(int stressLevel, int rri) {
        _stressLevel = stressLevel;
        _rri = rri;
        _stressView.showPopup();
    }

    public void confirmStress() {
        _stressView.hidePopup();
        if (!_userSettings.isGeolocationSending()) {
            sendMessage(_context.getString(R.string.subject_stress_validated_by_user),
                    _context.getString(R.string.text_stress_validated_by_user),
                    _context.getString(R.string.coach_email));
            _stressView.openProblemCategoriesActivity();
        } else if (_stressView.checkPermissions()) {
            LocationManager.getInstance(_context).start((int)_stressLevel, _rri);
            sendMessage(_context.getString(R.string.subject_stress_validated_by_user),
                    _context.getString(R.string.text_stress_validated_by_user),
                    _context.getString(R.string.coach_email));
            _stressView.openProblemCategoriesActivity();
        } else {
            _stressView.requestPermisions();
        }
    }

    public void cancel() {
        _stressView.hidePopup();
        sendMessage(_context.getString(R.string.subject_stress_not_validated_by_user),
                _context.getString(R.string.text_stress_not_validated_by_user),
                _context.getString(R.string.coach_email));
    }

    public void sendMessage(String subject, String text, String mainCoachEmail) {
        String username = _patient.getFirstName() + " " + _patient.getLastName();
        String from = username + " <" + _patient.getEmail() + ">";
        subject = subject.replace("_username_", username);
        text = text.replace("_username_", username);

        new MailgunSender().run(from, _coach.getEmail(), mainCoachEmail, subject, text, null);
        if (_coachFirebaseToken != null)
            _notificationSender.run(_patient.getId(), _coachFirebaseToken, subject, text, "PatientActivity");
    }
}
