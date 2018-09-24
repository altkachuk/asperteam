package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ActivitySectorInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.JobInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LanguageInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.ActivitySector;
import atproj.cyplay.com.asperteamapi.model.FacebookProfile;
import atproj.cyplay.com.asperteamapi.model.Job;
import atproj.cyplay.com.asperteamapi.model.Language;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseResourceActivity;
import com.cyplay.atproj.asperteam.ui.customview.ProfileTextItemView;
import com.cyplay.atproj.asperteam.utils.FacebookManager;

import atproj.cyplay.com.asperteamapi.util.CalendarUtil;
import atproj.cyplay.com.asperteamapi.util.StringHelper;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 06-Apr-18.
 */

public class MyProfileActivity extends BaseResourceActivity {

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    JobInteractor jobInteractor;

    @Inject
    LanguageInteractor languageInteractor;

    @Inject
    ActivitySectorInteractor activitySectorInteractor;

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    Picasso picasso;

    @Inject
    FacebookManager facebook;

    @BindView(R.id.photoImage)
    ImageView photoImage;

    @BindView(R.id.nameText)
    TextView nameText;

    @BindView(R.id.ageText)
    TextView ageText;

    @BindView(R.id.sexText)
    TextView sexText;

    @BindView(R.id.experienceText)
    TextView experienceText;

    @BindView(R.id.functionItem)
    ProfileTextItemView functionItem;

    @BindView(R.id.companyItem)
    ProfileTextItemView companyItem;

    @BindView(R.id.durationItem)
    ProfileTextItemView durationItem;

    @BindView(R.id.emailText)
    TextView emailText;

    @BindView(R.id.phoneText)
    TextView phoneText;

    @BindView(R.id.emergencyContactText)
    TextView emergencyContactText;

    @BindView(R.id.personalAddressText)
    TextView personalAddressText;

    @BindView(R.id.companyAddressText)
    TextView companyAddressText;

    @BindView(R.id.leftHandedText)
    TextView leftHandedText;

    @BindView(R.id.rqthRecognitionText)
    TextView rqthRecognitionText;

    @BindView(R.id.rqthRecognitionRenewDtText)
    TextView rqthRecognitionRenewDtText;

    @BindView(R.id.mdphNotificationText)
    TextView mdphNotificationText;

    @BindView(R.id.jobConversionByClinicText)
    TextView jobConversionByClinicText;

    @BindView(R.id.jobConversionWantedText)
    TextView jobConversionWantedText;

    @BindView(R.id.jobConversionApprovedByCompanyText)
    TextView jobConversionApprovedByCompanyText;

    @BindView(R.id.languageItem)
    ProfileTextItemView languageItem;

    @BindView(R.id.talentOrHobbiesText)
    TextView talentOrHobbiesText;

    @BindView(R.id.lastQualificationText)
    TextView lastQualificationText;

    @BindView(R.id.activitySectorItem)
    ProfileTextItemView activitySectorItem;

    @BindView(R.id.subscribedAtText)
    TextView subscribedAtText;

    @BindView(R.id.shareBestPracticeText)
    TextView shareBestPracticeText;

    @BindView(R.id.facebookButton)
    TextView facebookButton;

    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        clear();
    }

    private void clear() {
        nameText.setText("");
        ageText.setText("");
        sexText.setText("");
        experienceText.setText("");
        functionItem.setValue("");
        companyItem.setValue("");
        durationItem.setValue("");
        emailText.setText("");
        phoneText.setText("");
        emergencyContactText.setText("");
        personalAddressText.setText("");
        companyAddressText.setText("");
        leftHandedText.setText("");
        rqthRecognitionText.setText("");
        rqthRecognitionRenewDtText.setText("");
        mdphNotificationText.setText("");
        jobConversionByClinicText.setText("");
        jobConversionWantedText.setText("");
        jobConversionApprovedByCompanyText.setText("");
        languageItem.setValue("");
        talentOrHobbiesText.setText("");
        lastQualificationText.setText("");
        activitySectorItem.setValue("");
        subscribedAtText.setText("");
        shareBestPracticeText.setText("");
    }

    @Override
    protected void load() {
        getPatient(userSettings.getId());
    }

    private void getPatient(String id) {
        showPreloader();

        profileInteractor.getPatient(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _user = user;
                userSettings.setStressLevelMin(_user.getPatient().getStressLevelMin());
                userSettings.setStressLevelMax(_user.getPatient().getStressLevelMax());
                picasso.load(user.getImage()).transform(new CircleTransform(getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);
                nameText.setText(user.getFirstName() + " " + user.getLastName());
                String age = CalendarUtil.birthDateToAge(user.getPatient().getBirthDate()) + " " + getString(R.string.years);
                ageText.setText(age);
                sexText.setText(StringHelper.getSex(user.getPatient().getSex(), getApplicationContext()));
                experienceText.setText(user.getExperience());
                emailText.setText(user.getEmail());
                phoneText.setText(user.getPhone());
                emergencyContactText.setText(user.getPatient().getEmergencyContact());
                personalAddressText.setText(user.getPatient().getPersonalAddress());
                companyAddressText.setText(user.getPatient().getCompanyAddress());
                leftHandedText.setText(StringHelper.getHanded(user.getPatient().isLeftHanded(), getApplicationContext()));
                rqthRecognitionText.setText(StringHelper.getBoolean(user.getPatient().isRqthRecognition(), getApplicationContext()));
                rqthRecognitionRenewDtText.setText(user.getPatient().getRqthRecognitionRenewDt());
                mdphNotificationText.setText(user.getPatient().getMdphNotification());
                jobConversionByClinicText.setText(StringHelper.getBoolean(user.getPatient().isJobConversionByClinic(), getApplicationContext()));
                jobConversionWantedText.setText(user.getPatient().getJobConversionWanted());
                jobConversionApprovedByCompanyText.setText(user.getPatient().getJobConversionApprovedByCompany());
                talentOrHobbiesText.setText(user.getPatient().getTalentOrHobbies());
                lastQualificationText.setText(user.getPatient().getLastQualification());
                subscribedAtText.setText(user.getPatient().getSubscribedAt());
                shareBestPracticeText.setText(StringHelper.getBoolean(user.getPatient().isShareBestPractice(), getApplicationContext()));

                if (user.getJobIds().size() > 0) {
                    functionItem.showPreloader();
                    companyItem.showPreloader();
                    durationItem.showPreloader();
                    getJob(user.getJobIds().get(0));
                }

                languageItem.showPreloader();
                getAllLanguages();


                if (user.getPatient().getActivitySector() != null) {
                    activitySectorItem.showPreloader();
                    getActivitySector(user.getPatient().getActivitySector());
                }
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void getJob(int id) {
        jobInteractor.getJob(id, new ResourceRequestCallback<Job>() {
            @Override
            public void onSucess(Job job) {
                functionItem.hidePreloader();
                companyItem.hidePreloader();
                durationItem.hidePreloader();
                functionItem.setValue(job.getTitle());
                companyItem.setValue(job.getCompany());
                durationItem.setValue(job.getDuration().toString());
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    private void getAllLanguages() {
        languageInteractor.getAllLanguages(new ResourceRequestCallback<List<Language>>() {
            @Override
            public void onSucess(List<Language> languages) {
                languageItem.hidePreloader();

                String languageValue = "";

                for (int i = 0; i < _user.getPatient().getLanguages().size(); i++) {
                    for (int j = 0; j < languages.size(); j++) {
                        if (languages.get(j).getId() == _user.getPatient().getLanguages().get(i)) {
                            String languageId = languages.get(j).getLanguage();
                            if (languageValue.length() == 0)
                                languageValue += StringHelper.byIdName(languageId, getApplicationContext());
                            else
                                languageValue += ", " + StringHelper.byIdName(languageId, getApplicationContext());
                        }
                    }
                }
                languageItem.setValue(languageValue);
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    private void getActivitySector(int id) {
        activitySectorInteractor.getActivitySector(id, new ResourceRequestCallback<ActivitySector>() {
            @Override
            public void onSucess(ActivitySector activitySector) {
                activitySectorItem.hidePreloader();
                activitySectorItem.setValue(activitySector.getTitle());
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionEdit:
                startEditMyProfileActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.facebookButton)
    public void onClickFacebookButton() {
        loginFB();
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
        }
    };

    private void startEditMyProfileActivity() {
        Intent editMyProfileIntent = new Intent(this.getApplicationContext(), EditMyProfileActivity.class);
        this.startActivityForResult(editMyProfileIntent, RequestCode.RELOAD_REQUEST);
    }

    @Override
    protected void backToAndUpdate() {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        setResult(Activity.RESULT_OK, profileIntent);
        finish();
    }
}
