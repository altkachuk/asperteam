package com.cyplay.atproj.asperteam.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseResourceActivity;
import com.cyplay.atproj.asperteam.ui.fragment.AddSituationFragment;
import com.cyplay.atproj.asperteam.utils.ApplicationUtil;

/**
 * Created by andre on 23-Apr-18.
 */

public class MySuggestionsActivity extends BaseResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suggestions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        AddSituationFragment addSituationFragment = (AddSituationFragment) getFragmentManager().findFragmentById(R.id.addSituationFragment);
        addSituationFragment.setSubjectRes(R.string.subject_suggestions_add_situation);
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        ApplicationUtil.startActivityFinishWithIgnoreBatterOptimization(this, HomeActivity.class);
    }
}
