package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ActivitySectorInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.JobInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.LanguageInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.ActivitySector;
import atproj.cyplay.com.asperteamapi.model.Job;
import atproj.cyplay.com.asperteamapi.model.Language;
import atproj.cyplay.com.asperteamapi.model.Patient;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import com.cyplay.atproj.asperteam.ui.customview.MultiSelectionSpinner;
import com.cyplay.atproj.asperteam.ui.customview.ProfileDateSpinnerView;
import com.cyplay.atproj.asperteam.ui.customview.ProfileSpinnerView;
import atproj.cyplay.com.asperteamapi.util.ActivityHelper;
import atproj.cyplay.com.asperteamapi.util.StringHelper;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by andre on 06-Apr-18.
 */

public class EditMyProfileActivity extends EditProfileActivity {

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    JobInteractor jobInteractor;

    @Inject
    LanguageInteractor languageInteractor;

    @Inject
    ActivitySectorInteractor activitySectorInteractor;

    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;

    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;

    @BindView(R.id.birthdayEditItem)
    ProfileDateSpinnerView birthdayEditItem;

    @BindView(R.id.sexEditItem)
    ProfileSpinnerView sexEditItem;

    @BindView(R.id.experienceEditText)
    EditText experienceEditText;

    @BindView(R.id.functionEditText)
    EditText functionEditText;

    @BindView(R.id.companyEditText)
    EditText companyEditText;

    @BindView(R.id.durationEditText)
    EditText durationEditText;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.phoneEditText)
    EditText phoneEditText;

    @BindView(R.id.emergencyContactEditText)
    EditText emergencyContactEditText;

    @BindView(R.id.personalAddressEditText)
    EditText personalAddressEditText;

    @BindView(R.id.companyAddressEditText)
    EditText companyAddressEditText;

    @BindView(R.id.leftHanddedItem)
    ProfileSpinnerView leftHanddedEditItem;

    @BindView(R.id.rqthRecognitionEditItem)
    ProfileSpinnerView rqthRecognitionEditItem;

    @BindView(R.id.rqthRecognitionRenewDtEditItem)
    ProfileDateSpinnerView rqthRecognitionRenewDtEditItem;

    @BindView(R.id.mdphNotificationEditText)
    EditText mdphNotificationEditText;

    @BindView(R.id.jobConversionByClinicEditItem)
    ProfileSpinnerView jobConversionByClinicEditItem;

    @BindView(R.id.jobConversionWantedEditText)
    EditText jobConversionWantedEditText;

    @BindView(R.id.jobConversionApprovedByCompanyEditText)
    EditText jobConversionApprovedByCompanyEditText;

    @BindView(R.id.languageSpinner)
    MultiSelectionSpinner languageSpinner;

    @BindView(R.id.talentOrHobbiesEditText)
    EditText talentOrHobbiesEditText;

    @BindView(R.id.lastQualificationEditText)
    EditText lastQualificationEditText;

    @BindView(R.id.activitySectorEditItem)
    ProfileSpinnerView activitySectorEditItem;

    @BindView(R.id.subscribedAtEditText)
    EditText subscribedAtEditText;

    @BindView(R.id.shareBestPracticeEditItem)
    ProfileSpinnerView shareBestPracticeEditItem;

    private List<Language> _languages;

    private Patient _updatedPatient;
    private Job _updatedJob;

    private boolean _loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        clear();
        getPatient(userSettings.getId());

        birthdayEditItem.setOnValueChangedListener(onValueChangedBirthday);
        sexEditItem.setOnValueChangedListener(onValueChnagedSex);
        leftHanddedEditItem.setOnValueChangedListener(onValueChangedLeftHandded);
        rqthRecognitionEditItem.setOnValueChangedListener(onValueChangedRqthRecognition);
        rqthRecognitionRenewDtEditItem.setOnValueChangedListener(onValueChangedRqthRecognitionRenewDt);
        jobConversionByClinicEditItem.setOnValueChangedListener(onValueChangedJobConversionByClinic);
        languageSpinner.setListener(onMultiSelectionLanguage);
        activitySectorEditItem.setOnValueChangedListener(onValueChangedActivitySector);
        shareBestPracticeEditItem.setOnValueChangedListener(onValueChangedShareBestPractice);

        _updatedUser = new User();
        _updatedPatient = new Patient();
        _updatedJob = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void clear() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        //birthdayEditItem;
        //sexEditItem;
        experienceEditText.setText("");
        functionEditText.setText("");
        companyEditText.setText("");
        durationEditText.setText("");
        emailEditText.setText("");
        phoneEditText.setText("");
        emergencyContactEditText.setText("");
        personalAddressEditText.setText("");
        companyAddressEditText.setText("");
        //leftHanddedEditItem;
        //rqthRecognitionEditItem;
        //rqthRecognitionRenewDtEditItem;
        mdphNotificationEditText.setText("");
        //jobConversionByClinicEditText;
        jobConversionWantedEditText.setText("");
        jobConversionApprovedByCompanyEditText.setText("");
        //languageEditText;
        talentOrHobbiesEditText.setText("");
        lastQualificationEditText.setText("");
        //activitySectorEditItem;
        subscribedAtEditText.setText("");
        //shareBestPracticeEditItem;
    }

    private void getPatient(String id) {
        showPreloader();

        profileInteractor.getPatient(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                setUser(user);
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    protected void setUser(User user) {
        super.setUser(user);

        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        if (user.getPatient().getBirthDate() != null)
            birthdayEditItem.setDate(user.getPatient().getBirthDate());
        experienceEditText.setText(user.getExperience());
        emailEditText.setText(user.getEmail());
        phoneEditText.setText(user.getPhone());
        emergencyContactEditText.setText(user.getPatient().getEmergencyContact());
        personalAddressEditText.setText(user.getPatient().getPersonalAddress());
        companyAddressEditText.setText(user.getPatient().getCompanyAddress());
        if (user.getPatient().getRqthRecognitionRenewDt() != null)
            rqthRecognitionRenewDtEditItem.setDate(user.getPatient().getRqthRecognitionRenewDt());
        mdphNotificationEditText.setText(user.getPatient().getMdphNotification());
        jobConversionWantedEditText.setText(user.getPatient().getJobConversionWanted());
        jobConversionApprovedByCompanyEditText.setText(user.getPatient().getJobConversionApprovedByCompany());
        talentOrHobbiesEditText.setText(user.getPatient().getTalentOrHobbies());
        lastQualificationEditText.setText(user.getPatient().getLastQualification());
        subscribedAtEditText.setText(user.getPatient().getSubscribedAt());

        String[] sexItems = new String[]{
                getApplicationContext().getString(R.string.male),
                getApplicationContext().getString(R.string.female),
                getApplicationContext().getString(R.string.another)
        };
        Integer[] sexValues = new Integer[]{1, 2, 3};
        sexEditItem.setItems(sexItems, sexValues);
        sexEditItem.setSelectedItem(user.getPatient().getSex());

        String[] rqthRecognitionItems = new String[]{
                getApplicationContext().getString(R.string.yes),
                getApplicationContext().getString(R.string.no)
        };
        Boolean[] rqthRecognitionValues = new Boolean[]{true, false};
        rqthRecognitionEditItem.setItems(rqthRecognitionItems, rqthRecognitionValues);
        rqthRecognitionEditItem.setSelectedItem(user.getPatient().isRqthRecognition());

        String[] jobConversionByClinicItems = new String[]{
                getApplicationContext().getString(R.string.yes),
                getApplicationContext().getString(R.string.no)
        };
        Boolean[] jobConversionByClinicValues = new Boolean[]{true, false};
        jobConversionByClinicEditItem.setItems(jobConversionByClinicItems, jobConversionByClinicValues);
        jobConversionByClinicEditItem.setSelectedItem(user.getPatient().isJobConversionByClinic());

        String[] leftHandedItems = new String[]{
                getApplicationContext().getString(R.string.right_handed),
                getApplicationContext().getString(R.string.left_handed)};
        Boolean[] leftHandedValues = new Boolean[]{false, true};
        leftHanddedEditItem.setItems(leftHandedItems, leftHandedValues);
        leftHanddedEditItem.setSelectedItem(user.getPatient().isLeftHanded());

        String[] shareBestPracticeItems = new String[]{
                getApplicationContext().getString(R.string.yes),
                getApplicationContext().getString(R.string.no)
        };
        Boolean[] shareBestPracticeValues = new Boolean[]{true, false};
        shareBestPracticeEditItem.setItems(shareBestPracticeItems, shareBestPracticeValues);
        shareBestPracticeEditItem.setSelectedItem(user.getPatient().isShareBestPractice());

        if (user.getJobIds().size() > 0) {
            getJob(user.getJobIds().get(0));
        }

        getAllLanguages();

        getAllActivitySectors();

        _updatedPatient.setUser(user.getId());

        _loaded = true;
    }

    private void getJob(int id) {
        jobInteractor.getJob(id, new ResourceRequestCallback<Job>() {
            @Override
            public void onSucess(Job job) {
                functionEditText.setText(job.getTitle());
                companyEditText.setText(job.getCompany());
                durationEditText.setText(job.getDuration().toString());
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
                _languages = languages;

                String[] languageItems = new String[languages.size()];
                int[] selections = new int[_currentUser.getPatient().getLanguages().size()];

                int selectionCnt = 0;
                for (int i = 0; i < languages.size(); i++) {
                    languageItems[i] = StringHelper.byIdName(languages.get(i).getLanguage(), getApplicationContext());

                    for (int j = 0; j < _currentUser.getPatient().getLanguages().size(); j++) {
                        if (languages.get(i).getId() == _currentUser.getPatient().getLanguages().get(j)) {
                            selections[selectionCnt] = i;
                            selectionCnt++;
                        }
                    }
                }

                languageSpinner.setItems(languageItems);
                languageSpinner.setSelection(selections);
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    private void getAllActivitySectors() {
        activitySectorInteractor.getAllActivitySectors(new ResourceRequestCallback<List<ActivitySector>>() {
            @Override
            public void onSucess(List<ActivitySector> activitySectors) {
                String[] activitySectorItems = new String[activitySectors.size()];
                Integer[] activitySectorValues = new Integer[activitySectors.size()];

                for (int i = 0; i < activitySectors.size(); i++) {
                    activitySectorItems[i] = activitySectors.get(i).getTitle();
                    activitySectorValues[i] = activitySectors.get(i).getId();
                }

                activitySectorEditItem.setItems(activitySectorItems, activitySectorValues);
                activitySectorEditItem.setSelectedItem(_currentUser.getPatient().getActivitySector());
            }

            @Override
            public void onError(BaseException e) {

            }
        });
    }

    @OnClick(R.id.editPhotoButton)
    public void onClickPhotoButton() {
        openSelectPhotoPopup();
    }

    @OnTextChanged(value = R.id.firstNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedFirstName(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setFirstName(editable.toString());
    }

    @OnTextChanged(value = R.id.lastNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedLastName(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setLastName(editable.toString());
    }

    private ProfileDateSpinnerView.OnValueChangedListener onValueChangedBirthday = new ProfileDateSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(String value) {
            if (!_loaded)
                return;
            _updatedPatient.setBirthDate(value);
        }
    };

    private ProfileSpinnerView.OnValueChangedListener onValueChnagedSex = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setSex(Integer.parseInt(value.toString()));
        }
    };

    @OnTextChanged(value = R.id.experienceEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedExperience(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setExperience(editable.toString());
    }

    @OnTextChanged(value = R.id.functionEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedFunction(Editable editable) {
        if (!_loaded)
            return;
        generateJob();
        _updatedJob.setTitle(editable.toString());
    }

    @OnTextChanged(value = R.id.companyEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedCompany(Editable editable) {
        if (!_loaded)
            return;
        generateJob();
        _updatedJob.setCompany(editable.toString());
    }

    @OnTextChanged(value = R.id.durationEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedDuration(Editable editable) {
        if (!_loaded)
            return;
        generateJob();
        if (editable.toString().length() == 0)
            _updatedJob.setDuration(0.0);
        else
            _updatedJob.setDuration(Double.parseDouble(editable.toString()));
    }

    @OnTextChanged(value = R.id.emailEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedEmail(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setEmail(editable.toString());
    }

    @OnTextChanged(value = R.id.phoneEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedPhone(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setPhone(editable.toString());
    }

    @OnTextChanged(value = R.id.emergencyContactEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedEmergencyContact(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setEmergencyContact(editable.toString());
    }

    @OnTextChanged(value = R.id.personalAddressEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedPersonalAddress(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setPersonalAddress(editable.toString());
    }

    @OnTextChanged(value = R.id.companyAddressEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedCompanyAddress(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setCompanyAddress(editable.toString());
    }

    private ProfileSpinnerView.OnValueChangedListener onValueChangedLeftHandded = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setLeftHanded(Boolean.parseBoolean(value.toString()));
        }
    };

    private ProfileSpinnerView.OnValueChangedListener onValueChangedRqthRecognition = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setRqthRecognition(Boolean.parseBoolean(value.toString()));
        }
    };

    private ProfileDateSpinnerView.OnValueChangedListener onValueChangedRqthRecognitionRenewDt = new ProfileDateSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(String value) {
            if (!_loaded)
                return;
            _updatedPatient.setRqthRecognitionRenewDt(value);
        }
    };

    @OnTextChanged(value = R.id.mdphNotificationEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedMdphNotification(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setMdphNotification(editable.toString());
    }

    private ProfileSpinnerView.OnValueChangedListener onValueChangedJobConversionByClinic = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setJobConversionByClinic(Boolean.parseBoolean(value.toString()));
        }
    };

    @OnTextChanged(value = R.id.jobConversionWantedEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedJobConversionWanted(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setJobConversionWanted(editable.toString());
    }

    @OnTextChanged(value = R.id.jobConversionApprovedByCompanyEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedJobConversionApprovedByCompany(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setJobConversionApprovedByCompany(editable.toString());
    }

    private MultiSelectionSpinner.OnMultipleItemsSelectedListener onMultiSelectionLanguage = new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
        @Override
        public void selectedIndices(List<Integer> indices) {
            if (!_loaded)
                return;
            List<Integer> languages = new LinkedList<>();
            for (int i = 0; i < indices.size(); i++) {
                languages.add(_languages.get(indices.get(i)).getId());
            }
            _updatedPatient.setLanguage(languages);
        }

        @Override
        public void selectedStrings(List<String> strings) {

        }
    };

    private ProfileSpinnerView.OnValueChangedListener onValueChangedActivitySector = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setActivitySector(Integer.parseInt(value.toString()));
        }
    };

    @OnTextChanged(value = R.id.talentOrHobbiesEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedTalentOrHobbies(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setTalentOrHobbies(editable.toString());
    }

    @OnTextChanged(value = R.id.lastQualificationEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedLastQualification(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setLastQualification(editable.toString());
    }

    @OnTextChanged(value = R.id.subscribedAtEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedSubscribedAt(Editable editable) {
        if (!_loaded)
            return;
        _updatedPatient.setSubscribedAt(editable.toString());
    }

    private ProfileSpinnerView.OnValueChangedListener onValueChangedShareBestPractice = new ProfileSpinnerView.OnValueChangedListener() {
        @Override
        public void onChanged(Object value) {
            if (!_loaded)
                return;
            _updatedPatient.setShareBestPractice(Boolean.parseBoolean(value.toString()));
        }
    };


    private void generateJob() {
        if (_updatedJob == null)
            _updatedJob = new Job();
        if (_currentUser.getJobIds().size() > 0)
            _updatedJob.setId(_currentUser.getJobIds().get(0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDone:
                ActivityHelper.hideKeyboard(this);
                if (_updated)
                    backToAndUpdate();
                else if (!_updatedUser.isEmpty() || !_updatedPatient.isEmpty())
                    updateUser(_currentUser.getId(), _currentUser.getUsername(), _updatedUser);
                else if (_updatedJob != null)
                    updateOrCreateJob();
                else
                    supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void updateUser(String id, String username, User user) {
        showPreloader();
        _updatedUser.setPatient(_updatedPatient);
        profileInteractor.updateUser(id, username, user, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();

                if (_updatedJob != null)
                    updateOrCreateJob();
                else
                    backToAndUpdate();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void updateOrCreateJob() {
        if (_updatedJob.getId() != null)
            updateJob(_updatedJob.getId(), _updatedJob);
        else
            createJob(_updatedJob);
    }

    private void updateJob(int jobId, Job job) {
        showPreloader();
        jobInteractor.updateJob(jobId, job, new ResourceRequestCallback<Job>() {
            @Override
            public void onSucess(Job job) {
                hidePreloader();
                backToAndUpdate();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void createJob(Job job) {
        showPreloader();
        jobInteractor.createJob(job, new ResourceRequestCallback<Job>() {
            @Override
            public void onSucess(Job job) {
                hidePreloader();
                backToAndUpdate();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @Override
    protected void backToAndUpdate() {
        Intent myProfileIntent = new Intent(this, MyProfileActivity.class);
        setResult(Activity.RESULT_OK, myProfileIntent);
        finish();
    }
}
