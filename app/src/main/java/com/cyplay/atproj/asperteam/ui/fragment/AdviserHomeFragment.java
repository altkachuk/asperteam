package com.cyplay.atproj.asperteam.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileAdminInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Advice;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseResourceFragment;

import atproj.cyplay.com.asperteamapi.util.CalendarUtil;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by andre on 17-Apr-18.
 */

public class AdviserHomeFragment extends BaseResourceFragment {

    @Inject
    SituationInteractor situationInteractor;

    @Inject
    ProfileAdminInteractor profileAdminInteractor;

    @Inject
    Picasso picasso;

    @BindView(R.id.photoImage)
    ImageView photoImage;

    @BindView(R.id.nameText)
    TextView nameText;

    @BindView(R.id.ageText)
    TextView ageText;

    @BindView(R.id.descriptionText)
    TextView descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adviser_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clear();
        load();
    }

    private void clear() {
        nameText.setText("");
        ageText.setText("");
        descriptionText.setText("");
    }

    @Override
    public void load() {
        getAdvice();
    }

    private void getAdvice() {
        showPreloader();
        situationInteractor.getAdvices(CalendarUtil.getTodayDate(), new ResourceRequestCallback<List<Advice>>() {
            @Override
            public void onSucess(List<Advice> advices) {
                hidePreloader();
                if (advices.size() > 0) {
                    descriptionText.setText(advices.get(0).getText());
                    getUser(advices.get(0).getUserId());
                }
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void getUser(final String userId) {
        showPreloader();
        profileAdminInteractor.getUser(userId, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                picasso.load(user.getImage()).transform(new CircleTransform(getActivity().getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);
                nameText.setText(user.getFirstName());
                String age = CalendarUtil.birthDateToAge(user.getPatient().getBirthDate()) + " " + getString(R.string.years);
                ageText.setText(age);
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }
}
