package com.cyplay.atproj.asperteam.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.widget.EditText;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.RoleType;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by andre on 06-Apr-18.
 */

public class EditStaffProfileActivity extends EditProfileActivity {

    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;

    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;

    @BindView(R.id.companyEditText)
    EditText companyEditText;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.phoneEditText)
    EditText phoneEditText;

    @BindView(R.id.experienceEditText)
    EditText experienceEditText;

    @BindView(R.id.officeNumberEditText)
    EditText officeNumberEditText;

    private String _id;
    private RoleType _roleType;

    private boolean _loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        _id = getIntent().getExtras().get("id").toString();
        String roleTypeExt = getIntent().getExtras().get("role_type").toString();
        _roleType = RoleType.values()[Integer.parseInt(roleTypeExt)];

        clear();

        getStaff(_id);

        _updatedUser = new User();
    }

    private void clear() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        companyEditText.setText("");
        emailEditText.setText("");
        phoneEditText.setText("");
        experienceEditText.setText("");
        officeNumberEditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getStaff(String id) {
        showPreloader();

        profileInteractor.getStaff(id, new ResourceRequestCallback<User>() {
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

    @Override
    protected void setUser(User user) {
        super.setUser(user);
        firstNameEditText.setText(_currentUser.getFirstName());
        lastNameEditText.setText(_currentUser.getLastName());
        companyEditText.setText(_currentUser.getCompany());
        emailEditText.setText(_currentUser.getEmail());
        phoneEditText.setText(_currentUser.getPhone());
        experienceEditText.setText(_currentUser.getExperience());
        officeNumberEditText.setText(_currentUser.getOfficeNumber());

        _loaded = true;
    }

    @OnClick(R.id.editPhotoButton)
    public void onClickPhotoButton() {
        openSelectPhotoPopup();
    }

    @OnTextChanged(value = R.id.firstNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextFirstName(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setFirstName(editable.toString());
    }

    @OnTextChanged(value = R.id.lastNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextLastName(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setLastName(editable.toString());
    }

    @OnTextChanged(value = R.id.companyEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextCompany(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setCompany(editable.toString());
    }

    @OnTextChanged(value = R.id.emailEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextEmail(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setEmail(editable.toString());
    }

    @OnTextChanged(value = R.id.experienceEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextExperience(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setExperience(editable.toString());
    }

    @OnTextChanged(value = R.id.phoneEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextPhone(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setPhone(editable.toString());
    }

    @OnTextChanged(value = R.id.officeNumberEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextOfficeNumber(Editable editable) {
        if (!_loaded)
            return;
        _updatedUser.setOfficeNumber(editable.toString());
    }

    @Override
    protected void backToAndUpdate() {
        Intent myProfileIntent = new Intent(this, StaffProfileActivity.class);
        setResult(Activity.RESULT_OK, myProfileIntent);
        finish();
    }
}
