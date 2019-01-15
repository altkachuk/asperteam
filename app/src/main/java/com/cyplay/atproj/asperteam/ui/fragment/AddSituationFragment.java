package com.cyplay.atproj.asperteam.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;
import atproj.cyplay.com.asperteamapi.util.ActivityHelper;
import com.cyplay.atproj.asperteam.utils.MailgunSender;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * Created by andre on 05-Apr-18.
 */

public class AddSituationFragment extends BaseResourceFragment {

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    ProfileInteractor profileInteractor;

    @BindView(R.id.situationEditText)
    EditText situationEditText;

    @BindView(R.id.sendButton)
    TextView sendButton;

    private int _subjectRes;
    private User _user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_situation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clear();
        load();
    }

    public void setSubjectRes(int value) {
        _subjectRes = value;
    }

    private void clear() {
        situationEditText.setText("");
    }

    @Override
    public void load() {
        getPatient(userSettings.getId());
    }

    private void getPatient(String id) {
        showPreloader();

        profileInteractor.getPatient(id, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _user = user;
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @OnTextChanged(value = R.id.situationEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedEmailText(Editable editable) {
        boolean sendEnable = situationEditText.getText().toString().length() > 0;
        sendButton.setEnabled(sendEnable);
    }

    @OnEditorAction({R.id.situationEditText})
    protected boolean onTextAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            ActivityHelper.hideKeyboard(getActivity());
            boolean sendEnable = situationEditText.getText().toString().length() > 0;
            if (sendEnable) {
                showPreloader();
                sendMessage(getString(R.string.situation_email), getString(R.string.coach_email), _subjectRes);
            }
            return true;
        }
        return false;
    }

    @OnClick(R.id.sendButton)
    public void onClickSendButton() {
        ActivityHelper.hideKeyboard(getActivity());
        showPreloader();
        sendMessage(getString(R.string.situation_email), getString(R.string.coach_email), _subjectRes);
    }

    private void sendMessage(String to, String cc, int subjectRes) {
        String username = _user.getFirstName() + " " + _user.getLastName();
        String from = username + " <" + _user.getEmail() + ">";
        String subject = getString(subjectRes).replace("_username_", username);
        String text = situationEditText.getText().toString();

        MailgunSender mailgunSender = new MailgunSender();
        mailgunSender.run(from, to, cc, subject, text, new MailgunSender.OnMailgunListener() {
            @Override
            public void onSend() {
                hidePreloader();
                getPopup().initPopup(0, getString(R.string.message_sent));
                getPopup().setPositive(getString(R.string.ok_button));
                getPopup().show();
            }
        });
    }
}
